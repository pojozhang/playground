# 索引优化

![版本](https://img.shields.io/badge/mysql-8.0.13-blue.svg)

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
explain select *
        from people
        where name = 'Peter';

+----+-------------+--------+------------+------+---------------+--------------+---------+-------+------+----------+-------------+
| id | select_type | table  | partitions | type | possible_keys | key          | key_len | ref   | rows | filtered | Extra       |
+----+-------------+--------+------------+------+---------------+--------------+---------+-------+------+----------+-------------+
|  1 | SIMPLE      | people | NULL       | ref  | index_people  | index_people | 83      | const |    1 |   100.00 | Using index |
+----+-------------+--------+------------+------+---------------+--------------+---------+-------+------+----------+-------------+

-- 用到了索引的第1列和第2列
explain select *
        from people
        where name = 'Peter'
          and country = 'China';

+----+-------------+--------+------------+------+---------------+--------------+---------+-------------+------+----------+-------------+
| id | select_type | table  | partitions | type | possible_keys | key          | key_len | ref         | rows | filtered | Extra       |
+----+-------------+--------+------------+------+---------------+--------------+---------+-------------+------+----------+-------------+
|  1 | SIMPLE      | people | NULL       | ref  | index_people  | index_people | 166     | const,const |    1 |   100.00 | Using index |
+----+-------------+--------+------------+------+---------------+--------------+---------+-------------+------+----------+-------------+

-- 用到了索引的第1列、第2列和第3列
explain select *
        from people
        where name = 'Peter'
          and country = 'China'
          and age = 18;

+----+-------------+--------+------------+------+---------------+--------------+---------+-------------------+------+----------+-------------+
| id | select_type | table  | partitions | type | possible_keys | key          | key_len | ref               | rows | filtered | Extra       |
+----+-------------+--------+------------+------+---------------+--------------+---------+-------------------+------+----------+-------------+
|  1 | SIMPLE      | people | NULL       | ref  | index_people  | index_people | 171     | const,const,const |    1 |   100.00 | Using index |
+----+-------------+--------+------------+------+---------------+--------------+---------+-------------------+------+----------+-------------+
```

以下语句中虽然条件的顺序不完全按照索引中列的顺序，但依然可以用到索引，因为MySQL的优化器会对索引的选择进行分析。

```sql
select *
from people
where age = 18
  and country = 'China'
  and name = 'Peter';

-- 和以下查询等价
select *
from people
where name = 'Peter'
  and country = 'China'
  and age = 18;
```

以下查询只能用到部分索引，可以看到它用到的索引的长度和`select * from people where name = 'Peter'`查询中用到的索引长度是一样的，说明索引只有第1列用到了。在`Extra`列中显示了`Using where; Using index`，说明是用了一部分索引后再逐行扫描。

```sql
-- 只能用到索引的第1列，因为没有匹配中间的country列
explain select *
        from people
        where name = 'Peter'
          and age = 18;

+----+-------------+--------+------------+------+---------------+--------------+---------+-------+------+----------+--------------------------+
| id | select_type | table  | partitions | type | possible_keys | key          | key_len | ref   | rows | filtered | Extra                    |
+----+-------------+--------+------------+------+---------------+--------------+---------+-------+------+----------+--------------------------+
|  1 | SIMPLE      | people | NULL       | ref  | index_people  | index_people | 83      | const |    1 |    20.00 | Using where; Using index |
+----+-------------+--------+------------+------+---------------+--------------+---------+-------+------+----------+--------------------------+
```

以下查询只能用到部分索引，这是因为`country > 'China'`是一个范围条件，而位于范围条件右边的所有列都不能利用索引查找。

```sql
explain select *
        from people
        where name = 'Peter'
          and country > 'China'
          and age = 18;

+----+-------------+--------+------------+-------+---------------+--------------+---------+------+------+----------+--------------------------+
| id | select_type | table  | partitions | type  | possible_keys | key          | key_len | ref  | rows | filtered | Extra                    |
+----+-------------+--------+------------+-------+---------------+--------------+---------+------+------+----------+--------------------------+
|  1 | SIMPLE      | people | NULL       | range | index_people  | index_people | 166     | NULL |    1 |    20.00 | Using where; Using index |
+----+-------------+--------+------------+-------+---------------+--------------+---------+------+------+----------+--------------------------+
```

以下查询不能利用索引，我们可以看到`possible_keys`是`NULL`。但是这里有个问题，执行计划显示`key`不为`NULL`并且`type`是`index`而不是代表全表扫描的`ALL`，这是为什么呢？

其实这里用到了**覆盖索引**。如果一个索引包含查询所需要的所有数据，那么这个索引就称为覆盖索引。它的好处是只需要扫描索引就能找到需要的全部数据而不用进行回表。扫描索引相比全表扫描的好处是索引的数据量通常较小，降低了IO操作的负担。

```sql
explain select *
        from people
        where country = 'China';

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
explain select *
        from ticket
        where actor_id = 100
           or film_id = 210
           or seat_no = 320;

+----+-------------+--------+------------+-------------+-----------------------------------+-----------------------------------+---------+------+------+----------+-------------------------------------------------------------+
| id | select_type | table  | partitions | type        | possible_keys                     | key                               | key_len | ref  | rows | filtered | Extra                                                       |
+----+-------------+--------+------------+-------------+-----------------------------------+-----------------------------------+---------+------+------+----------+-------------------------------------------------------------+
|  1 | SIMPLE      | ticket | NULL       | index_merge | index_actor,index_film,index_seat | index_actor,index_film,index_seat | 5,5,5   | NULL |    3 |   100.00 | Using union(index_actor,index_film,index_seat); Using where |
+----+-------------+--------+------------+-------------+-----------------------------------+-----------------------------------+---------+------+------+----------+-------------------------------------------------------------+
```

## 利用索引排序

当我们使用`ORDER BY`语句对查询结果进行排序时需要注意`ORDER BY`同样遵循最左匹配原则。我们应尽量利用索引排序，避免使用文件排序(File Sort)，因为前者效率更高。

下面的测试数据来自MySQL官方的sakila库，你可以通过以下两个sql文件进行导入。

- [sakila-schema.sql](resources/sakila-schema.sql)
- [sakila-data.sql](resources/sakila-data.sql)。

下面的例子中主要用到两张表，`rental`表和`staff`表，它们的表结构如下，这里省略了原来表中的一些索引和外键，因为在这个例子中不会被用到。

```sql
create table rental
(
  rental_id    int auto_increment primary key,
  rental_date  datetime                            not null,
  inventory_id mediumint unsigned                  not null,
  customer_id  smallint(5) unsigned                not null,
  return_date  datetime                            null,
  staff_id     tinyint unsigned                    not null,
  last_update  timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
  constraint rental_date unique (rental_date, inventory_id, customer_id)
)

create table staff
(
  staff_id    tinyint unsigned auto_increment primary key,
  first_name  varchar(45)                         not null,
  last_name   varchar(45)                         not null,
  address_id  smallint(5) unsigned                not null,
  picture     blob                                null,
  email       varchar(50)                         null,
  store_id    tinyint unsigned                    not null,
  active      tinyint(1) default '1'              not null,
  username    varchar(16)                         not null,
  password    varchar(40) collate utf8_bin        null,
  last_update timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP
)

create index idx_fk_address_id on staff (address_id);
```

以下几种情况可以利用索引排序，`Extra`列显示`Using index`，并且没有`Using filesort`。

1. 排序字段和索引字段完全匹配。

```sql
explain select rental_id
        from rental
        where rental_date > '2005-05-25'
        order by rental_date, inventory_id, customer_id;

+----+-------------+--------+------------+-------+---------------+-------------+---------+------+------+----------+--------------------------+
| id | select_type | table  | partitions | type  | possible_keys | key         | key_len | ref  | rows | filtered | Extra                    |
+----+-------------+--------+------------+-------+---------------+-------------+---------+------+------+----------+--------------------------+
|  1 | SIMPLE      | rental | NULL       | range | rental_date   | rental_date | 5       | NULL | 8004 |   100.00 | Using where; Using index |
+----+-------------+--------+------------+-------+---------------+-------------+---------+------+------+----------+--------------------------+
```

2. 排序字段和索引字段左边部分匹配。

```sql
explain select rental_id
        from rental
        where rental_date > '2005-05-25'
        order by rental_date, inventory_id;

+----+-------------+--------+------------+-------+---------------+-------------+---------+------+------+----------+--------------------------+
| id | select_type | table  | partitions | type  | possible_keys | key         | key_len | ref  | rows | filtered | Extra                    |
+----+-------------+--------+------------+-------+---------------+-------------+---------+------+------+----------+--------------------------+
|  1 | SIMPLE      | rental | NULL       | range | rental_date   | rental_date | 5       | NULL | 8004 |   100.00 | Using where; Using index |
+----+-------------+--------+------------+-------+---------------+-------------+---------+------+------+----------+--------------------------+
```

3. 排序字段的第一列在`WHERE`语句中被指定为常量时可以用到索引，如果条件改成`rental_date > '2005-05-25'`则会使用文件排序。

```sql
explain select rental_id
        from rental
        where rental_date = '2005-05-25'
        order by inventory_id, customer_id;

+----+-------------+--------+------------+------+---------------+-------------+---------+-------+------+----------+--------------------------+
| id | select_type | table  | partitions | type | possible_keys | key         | key_len | ref   | rows | filtered | Extra                    |
+----+-------------+--------+------------+------+---------------+-------------+---------+-------+------+----------+--------------------------+
|  1 | SIMPLE      | rental | NULL       | ref  | rental_date   | rental_date | 5       | const |    1 |   100.00 | Using where; Using index |
+----+-------------+--------+------------+------+---------------+-------------+---------+-------+------+----------+--------------------------+
```

4. 多张表联合查询时排序的字段全部是第一张表时可以利用索引。

```sql
explain select staff.staff_id
        from staff left join rental on staff.staff_id = rental.staff_id
        order by address_id;

+----+-------------+--------+------------+-------+-----------------+-------------------+---------+-----------------------+------+----------+-------------+
| id | select_type | table  | partitions | type  | possible_keys   | key               | key_len | ref                   | rows | filtered | Extra       |
+----+-------------+--------+------------+-------+-----------------+-------------------+---------+-----------------------+------+----------+-------------+
|  1 | SIMPLE      | staff  | NULL       | index | NULL            | idx_fk_address_id | 2       | NULL                  |    2 |   100.00 | Using index |
|  1 | SIMPLE      | rental | NULL       | ref   | idx_fk_staff_id | idx_fk_staff_id   | 1       | sakila.staff.staff_id | 8004 |   100.00 | Using index |
+----+-------------+--------+------------+-------+-----------------+-------------------+---------+-----------------------+------+----------+-------------+
```

以下情况会用到文件排序，`Extra`列显示`Using filesort`，性能较差。

1. 排序字段不符合最左匹配原则，比如中间缺少了一列。

```sql
explain select rental_id
        from rental
        where rental_date > '2005-05-25'
        order by rental_date, customer_id;

+----+-------------+--------+------------+-------+---------------+-------------+---------+------+------+----------+------------------------------------------+
| id | select_type | table  | partitions | type  | possible_keys | key         | key_len | ref  | rows | filtered | Extra                                    |
+----+-------------+--------+------------+-------+---------------+-------------+---------+------+------+----------+------------------------------------------+
|  1 | SIMPLE      | rental | NULL       | range | rental_date   | rental_date | 5       | NULL | 8004 |   100.00 | Using where; Using index; Using filesort |
+----+-------------+--------+------------+-------+---------------+-------------+---------+------+------+----------+------------------------------------------+
```

2. 排序字段同时出现`ASC`和`DESC`。

```sql
explain select rental_id
        from rental
        where rental_date > '2005-05-25'
        order by rental_date ASC, inventory_id DESC;

+----+-------------+--------+------------+-------+---------------+-------------+---------+------+------+----------+------------------------------------------+
| id | select_type | table  | partitions | type  | possible_keys | key         | key_len | ref  | rows | filtered | Extra                                    |
+----+-------------+--------+------------+-------+---------------+-------------+---------+------+------+----------+------------------------------------------+
|  1 | SIMPLE      | rental | NULL       | range | rental_date   | rental_date | 5       | NULL | 8004 |   100.00 | Using where; Using index; Using filesort |
+----+-------------+--------+------------+-------+---------------+-------------+---------+------+------+----------+------------------------------------------+
```

3. 查询的列不在索引中。比如以下查询的`staff_id`列不在索引中。

```sql
explain select staff_id
        from rental
        where rental_date > '2005-05-25'
        order by rental_date;

+----+-------------+--------+------------+------+---------------+------+---------+------+-------+----------+-----------------------------+
| id | select_type | table  | partitions | type | possible_keys | key  | key_len | ref  | rows  | filtered | Extra                       |
+----+-------------+--------+------------+------+---------------+------+---------+------+-------+----------+-----------------------------+
|  1 | SIMPLE      | rental | NULL       | ALL  | rental_date   | NULL | NULL    | NULL | 16008 |    50.00 | Using where; Using filesort |
+----+-------------+--------+------------+------+---------------+------+---------+------+-------+----------+-----------------------------+
```

同样的语句加上`limit`就可以利用索引。

```sql
explain select staff_id
        from rental
        where rental_date > '2005-05-25'
        order by rental_date
        limit 10;

+----+-------------+--------+------------+-------+---------------+-------------+---------+------+------+----------+-----------------------+
| id | select_type | table  | partitions | type  | possible_keys | key         | key_len | ref  | rows | filtered | Extra                 |
+----+-------------+--------+------------+-------+---------------+-------------+---------+------+------+----------+-----------------------+
|  1 | SIMPLE      | rental | NULL       | range | rental_date   | rental_date | 5       | NULL | 8002 |   100.00 | Using index condition |
+----+-------------+--------+------------+-------+---------------+-------------+---------+------+------+----------+-----------------------+
```

4. 排序的列不在索引中。列`staff_id`不在索引中。

```sql
explain select rental_id
        from rental
        where rental_date > '2005-05-25'
        order by rental_date, inventory_id, staff_id;

+----+-------------+--------+------------+------+---------------+------+---------+------+-------+----------+-----------------------------+
| id | select_type | table  | partitions | type | possible_keys | key  | key_len | ref  | rows  | filtered | Extra                       |
+----+-------------+--------+------------+------+---------------+------+---------+------+-------+----------+-----------------------------+
|  1 | SIMPLE      | rental | NULL       | ALL  | rental_date   | NULL | NULL    | NULL | 16008 |    50.00 | Using where; Using filesort |
+----+-------------+--------+------------+------+---------------+------+---------+------+-------+----------+-----------------------------+
```

5. 多张表联合查询时排序的字段不是来自于第一张表。以下查询中的`rental_date`列来自`rental`表而不是`staff`表。

```sql
explain select staff.staff_id
        from staff left join rental on staff.staff_id = rental.staff_id
        order by rental_date;

+----+-------------+--------+------------+-------+-----------------+-----------------+---------+-----------------------+------+----------+----------------------------------------------+
| id | select_type | table  | partitions | type  | possible_keys   | key             | key_len | ref                   | rows | filtered | Extra                                        |
+----+-------------+--------+------------+-------+-----------------+-----------------+---------+-----------------------+------+----------+----------------------------------------------+
|  1 | SIMPLE      | staff  | NULL       | index | NULL            | idx_fk_store_id | 1       | NULL                  |    2 |   100.00 | Using index; Using temporary; Using filesort |
|  1 | SIMPLE      | rental | NULL       | ref   | idx_fk_staff_id | idx_fk_staff_id | 1       | sakila.staff.staff_id | 8004 |   100.00 | NULL                                         |
+----+-------------+--------+------------+-------+-----------------+-----------------+---------+-----------------------+------+----------+----------------------------------------------+
```

## 索引列顺序的选择

在我们建立索引时，索引列的顺序是有讲究的，也就是说对于相同的查询，索引(a, b)和索引(b, a)的查询性能可能是不一样的。
通常我们用**选择性**来衡量一个索引的性能，选择性越大性能越好。

> 选择性 = 基数 / 总数

基数是指不重复的索引值的数量，比如某个索引的值有(1,2,3,4,1)5个元素，它的基数就是4，因为包含两个重复的元素1；总数就是表中数据总的行数。选择性的范围是(0, 1]，唯一索引的选择性是1，因此它的性能是最好的。

当我们建立索引(a, b)时，通常应该满足a的选择性大于b的选择性，这样会有较好的查询性能。

## 回表

如果一个查询语句通过某个二级索引进行搜索时会产生大量的回表操作，MySQL更倾向于使用全文扫描的方式进行查找。如果在查询语句后面加上LIMIT子句，则MySQL更倾向于使用二级索引+回表的方式进行查找。
