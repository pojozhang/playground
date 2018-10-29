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
