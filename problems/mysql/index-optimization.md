# 索引优化

## 最左匹配原则

假设我们有一张表，其建表语句如下，它有3列和一个索引。

```sql
create table people (
  name    varchar(20),
  country varchar(20),
  age     int,
  key index_people (name, country, age)
);
```

我们插入一些数据用来测试。

```sql
insert into people (name, country, age) values ('name1', 'China', 18);
insert into people (name, country, age) values ('name2', 'China', 18);
insert into people (name, country, age) values ('name3', 'China', 18);
insert into people (name, country, age) values ('name4', 'China', 18);
insert into people (name, country, age) values ('name5', 'China', 18);

select * from people;

+-------+---------+------+
| name  | country | age  |
+-------+---------+------+
| name1 | China   |   18 |
| name2 | China   |   18 |
| name3 | China   |   18 |
| name4 | China   |   18 |
| name5 | China   |   18 |
+-------+---------+------+
```

如果我们希望查询可以用到索引，那么查询条件中的列必须精确匹配索引的左边一个或连续的多个列。

以下几个查询是可以利用到索引的。

```sql
-- 用到了索引的第1列
select * from people where name = 'Peter';
-- 执行计划
+----+-------------+--------+------------+------+---------------+--------------+---------+-------+------+----------+-------------+
| id | select_type | table  | partitions | type | possible_keys | key          | key_len | ref   | rows | filtered | Extra       |
+----+-------------+--------+------------+------+---------------+--------------+---------+-------+------+----------+-------------+
|  1 | SIMPLE      | people | NULL       | ref  | index_people  | index_people | 83      | const |    1 |   100.00 | Using index |
+----+-------------+--------+------------+------+---------------+--------------+---------+-------+------+----------+-------------+

-- 用到了索引的第1列和第2列
select * from people where name = 'Peter' and country = 'China';
-- 执行计划
+----+-------------+--------+------------+------+---------------+--------------+---------+-------------+------+----------+-------------+
| id | select_type | table  | partitions | type | possible_keys | key          | key_len | ref         | rows | filtered | Extra       |
+----+-------------+--------+------------+------+---------------+--------------+---------+-------------+------+----------+-------------+
|  1 | SIMPLE      | people | NULL       | ref  | index_people  | index_people | 166     | const,const |    1 |   100.00 | Using index |
+----+-------------+--------+------------+------+---------------+--------------+---------+-------------+------+----------+-------------+

-- 用到了索引的第1列、第2列和第3列
select * from people where name = 'Peter' and country = 'China' and age = 18;
-- 执行计划
+----+-------------+--------+------------+------+---------------+--------------+---------+-------------------+------+----------+-------------+
| id | select_type | table  | partitions | type | possible_keys | key          | key_len | ref               | rows | filtered | Extra       |
+----+-------------+--------+------------+------+---------------+--------------+---------+-------------------+------+----------+-------------+
|  1 | SIMPLE      | people | NULL       | ref  | index_people  | index_people | 171     | const,const,const |    1 |   100.00 | Using index |
+----+-------------+--------+------------+------+---------------+--------------+---------+-------------------+------+----------+-------------+
```

以下语句中虽然条件的顺序不完全按照索引中列的顺序，但依然可以用到索引，因为MySQL的优化器会对索引的选择进行分析。

```sql
select * from people where age = 18 and country = 'China' and name = 'Peter';

-- 和以下查询等价
select * from people where name = 'Peter' and country = 'China' and age = 18;
```

以下查询只能用到部分索引，可以看到它用到的索引的长度和`select * from people where name = 'Peter'`查询中用到的索引长度是一样的，说明索引只有第1列用到了。在`Extra`列中显示了`Using where; Using index`，说明是用了一部分索引后再逐行扫描。

```sql
-- 只能用到索引的第1列，因为没有匹配中间的country列
select * from people where name = 'Peter' and age = 18;
-- 执行计划
+----+-------------+--------+------------+------+---------------+--------------+---------+-------+------+----------+--------------------------+
| id | select_type | table  | partitions | type | possible_keys | key          | key_len | ref   | rows | filtered | Extra                    |
+----+-------------+--------+------------+------+---------------+--------------+---------+-------+------+----------+--------------------------+
|  1 | SIMPLE      | people | NULL       | ref  | index_people  | index_people | 83      | const |    1 |    20.00 | Using where; Using index |
+----+-------------+--------+------------+------+---------------+--------------+---------+-------+------+----------+--------------------------+
```

以下查询只能用到部分索引，这是因为`country > 'China'`是一个范围条件，而位于范围条件右边的所有列都不能利用索引查找。

```sql
select * from people where name = 'Peter' and country > 'China' and age = 18;
-- 执行计划
+----+-------------+--------+------------+-------+---------------+--------------+---------+------+------+----------+--------------------------+
| id | select_type | table  | partitions | type  | possible_keys | key          | key_len | ref  | rows | filtered | Extra                    |
+----+-------------+--------+------------+-------+---------------+--------------+---------+------+------+----------+--------------------------+
|  1 | SIMPLE      | people | NULL       | range | index_people  | index_people | 166     | NULL |    1 |    20.00 | Using where; Using index |
+----+-------------+--------+------------+-------+---------------+--------------+---------+------+------+----------+--------------------------+
```

以下查询不能利用索引，我们可以看到`possible_keys`是`NULL`。但是这里有个问题，执行计划显示`key`不为`NULL`并且`type`是`index`而不是代表全表扫描的`ALL`，这是为什么呢？

其实这里用到了**覆盖索引**。如果一个索引包含查询所需要的所有数据，那么这个索引就称为覆盖索引。它的好处是只需要扫描索引就能找到需要的全部数据而不用进行回表。扫描索引相比全表扫描的好处是索引的数据量通常较小，降低了IO操作的负担。

```sql
select * from people where country = 'China';
-- 执行计划
+----+-------------+--------+------------+-------+---------------+--------------+---------+------+------+----------+--------------------------+
| id | select_type | table  | partitions | type  | possible_keys | key          | key_len | ref  | rows | filtered | Extra                    |
+----+-------------+--------+------------+-------+---------------+--------------+---------+------+------+----------+--------------------------+
|  1 | SIMPLE      | people | NULL       | index | NULL          | index_people | 171     | NULL |    5 |    20.00 | Using where; Using index |
+----+-------------+--------+------------+-------+---------------+--------------+---------+------+------+----------+--------------------------+
```

## 多列索引

当查询语句中有多个OR条件，我们需要为每一列创建独立的索引。

```sql
create table ticket (
  actor_id int,
  film_id  int,
  seat_no  int,
  key index_actor (actor_id),
  key index_film (film_id),
  key index_seat (seat_no)
);

-- 往里插一些数据
insert into ticket(actor_id, film_id, seat_no) VALUES (100, 200, 300);
insert into ticket(actor_id, film_id, seat_no) VALUES (110, 210, 310);
insert into ticket(actor_id, film_id, seat_no) VALUES (120, 220, 320);
insert into ticket(actor_id, film_id, seat_no) VALUES (130, 230, 330);
insert into ticket(actor_id, film_id, seat_no) VALUES (140, 240, 340);
insert into ticket(actor_id, film_id, seat_no) VALUES (150, 250, 350);
insert into ticket(actor_id, film_id, seat_no) VALUES (160, 260, 360);
insert into ticket(actor_id, film_id, seat_no) VALUES (170, 270, 370);
insert into ticket(actor_id, film_id, seat_no) VALUES (180, 280, 380);
insert into ticket(actor_id, film_id, seat_no) VALUES (190, 290, 390);
```

对于以下的查询的执行计划我们可以看到，`type`的值是`index_merge`，表示使用了**索引合并**，这是一种对多个索引分别进行扫描并将各自的结果进行合并的技术，从MySQL 5.1版本中引入。索引合并共有三种方式：并集、交集、先交后并。执行计划中的`Extra`显示`Using union`，因此这里用的是多个索引扫描后各自结果的并集。

```sql
select * from ticket where actor_id = 100 or film_id = 210 or seat_no = 320;
-- 执行计划
+----+-------------+--------+------------+-------------+-----------------------------------+-----------------------------------+---------+------+------+----------+-------------------------------------------------------------+
| id | select_type | table  | partitions | type        | possible_keys                     | key                               | key_len | ref  | rows | filtered | Extra                                                       |
+----+-------------+--------+------------+-------------+-----------------------------------+-----------------------------------+---------+------+------+----------+-------------------------------------------------------------+
|  1 | SIMPLE      | ticket | NULL       | index_merge | index_actor,index_film,index_seat | index_actor,index_film,index_seat | 5,5,5   | NULL |    3 |   100.00 | Using union(index_actor,index_film,index_seat); Using where |
+----+-------------+--------+------------+-------------+-----------------------------------+-----------------------------------+---------+------+------+----------+-------------------------------------------------------------+
```

## 索引列顺序的选择

在我们建立索引时，索引列的顺序是有讲究的，也就是说对于相同的查询，索引(a, b)和索引(b, a)的查询性能可能是不一样的。
通常我们用**选择性**来衡量一个索引的性能，选择性越大性能越好。

> 选择性 = 基数 / 总数

基数是指不重复的索引值的数量，比如某个索引的值有(1,2,3,4,1)5个元素，它的基数就是4，因为包含两个重复的元素1；总数就是表中数据总的行数。选择性的范围是(0, 1]，唯一索引的选择性是1，因此它的性能是最好的。

当我们建立索引(a, b)时，通常应该满足a的选择性大于b的选择性，这样会有较好的查询性能。

## 聚簇索引

```sql
OPTIMIZE TABLE
```