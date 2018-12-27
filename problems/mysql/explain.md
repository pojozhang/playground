# EXPLAIN

![版本](https://img.shields.io/badge/mysql-8.0.13-blue.svg)

本文测试数据来自MySQL官方的sakila库，你可以通过以下两个sql文件进行导入。

- [sakila-schema.sql](resources/sakila-schema.sql)
- [sakila-data.sql](resources/sakila-data.sql)。

我们通常在查询语句前加上`EXPLAIN`命令查看MySQL的执行计划从而对查询进行优化，下面看一个简单的例子。

```sql
explain select *
        from actor,
             film_actor,
             film
        where actor.actor_id = film_actor.actor_id
          and film_actor.film_id = film.film_id;

+----+-------------+------------+------------+--------+------------------------+---------+---------+---------------------------+------+----------+-------+
| id | select_type | table      | partitions | type   | possible_keys          | key     | key_len | ref                       | rows | filtered | Extra |
+----+-------------+------------+------------+--------+------------------------+---------+---------+---------------------------+------+----------+-------+
|  1 | SIMPLE      | actor      | NULL       | ALL    | PRIMARY                | NULL    | NULL    | NULL                      |  200 |   100.00 | NULL  |
|  1 | SIMPLE      | film_actor | NULL       | ref    | PRIMARY,idx_fk_film_id | PRIMARY | 2       | sakila.actor.actor_id     |   27 |   100.00 | NULL  |
|  1 | SIMPLE      | film       | NULL       | eq_ref | PRIMARY                | PRIMARY | 2       | sakila.film_actor.film_id |    1 |   100.00 | NULL  |
+----+-------------+------------+------------+--------+------------------------+---------+---------+---------------------------+------+----------+-------+
```

下面我们对`EXPLAIN`命令执行结果中的每一列进行解释。

## id

`id`是查询中执行`SELECT`子句或者是操作表的顺。，如果查询语句中有子查询或`UNION`查询，那么`id`会递增，`id`越大的查询优先级越高，查询从`id`最大的开始执行，如下所示。

```sql
explain select *
        from film_actor
        where actor_id = (select actor_id from actor where first_name = 'BOB');

+----+-------------+------------+------------+------+---------------+---------+---------+-------+------+----------+-------------+
| id | select_type | table      | partitions | type | possible_keys | key     | key_len | ref   | rows | filtered | Extra       |
+----+-------------+------------+------------+------+---------------+---------+---------+-------+------+----------+-------------+
|  1 | PRIMARY     | film_actor | NULL       | ref  | PRIMARY       | PRIMARY | 2       | const |   25 |   100.00 | Using where |
|  2 | SUBQUERY    | actor      | NULL       | ALL  | NULL          | NULL    | NULL    | NULL  |  200 |    10.00 | Using where |
+----+-------------+------------+------------+------+---------------+---------+---------+-------+------+----------+-------------+
```

否则`EXPLAIN`执行结果中的每一行`id`都是1，查询从上往下执行。

```sql
explain select *
        from film
               left join film_actor on film.film_id = film_actor.film_id;

+----+-------------+------------+------------+------+----------------+----------------+---------+---------------------+------+----------+-------+
|  1 | SIMPLE      | film       | NULL       | ALL  | NULL           | NULL           | NULL    | NULL                | 1000 |   100.00 | NULL  |
|  1 | SIMPLE      | film_actor | NULL       | ref  | idx_fk_film_id | idx_fk_film_id | 2       | sakila.film.film_id |    5 |   100.00 | NULL  |
+----+-------------+------------+------------+------+----------------+----------------+---------+---------------------+------+----------+-------+
```

有一种特殊情况下`id`的值是`NULL`，看下面的`UNION`查询，其中第三行的`<union1,2>`是MySQL创建的匿名临时表，它不出现在SQL语句中，因此对应的`id`是`NULL`。

```sql
explain select *
        from actor
        where first_name = 'BOB'
        union
        select *
        from actor
        where first_name = 'DAN';

+----+--------------+------------+------------+------+---------------+------+---------+------+------+----------+-----------------+
| id | select_type  | table      | partitions | type | possible_keys | key  | key_len | ref  | rows | filtered | Extra           |
+----+--------------+------------+------------+------+---------------+------+---------+------+------+----------+-----------------+
|  1 | PRIMARY      | actor      | NULL       | ALL  | NULL          | NULL | NULL    | NULL |  200 |    10.00 | Using where     |
|  2 | UNION        | actor      | NULL       | ALL  | NULL          | NULL | NULL    | NULL |  200 |    10.00 | Using where     |
| NULL | UNION RESULT | <union1,2> | NULL       | ALL  | NULL          | NULL | NULL    | NULL | NULL |     NULL | Using temporary |
+----+--------------+------------+------------+------+---------------+------+---------+------+------+----------+-----------------+
```

## select_type

表示对应的查询类型，主要有以下几种。

- SIMPLE

简单查询，指不含子查询或`UNION`查询。

- PRIMARY

除了简单查询外，剩下的几种类型统称为复杂查询。`PRIMARY`代表复杂查询的最外层部分。

- SUBQUERY

查询语句中的子查询。

- DERIVED

同样也表示查询语句中的子查询，和`SUBQUERY`的区别是`DERIVED`表示在`from`中的子查询，而`SUBQUERY`不在`from`中。

`DERIVED`是派生的意思，表示从某个子查询中派生而来，下面的例子中`select count(film_id) c from film group by release_year`这一句会生成一张临时表，这张临时表就是派生自子查询，也称为派生表。

```sql
explain select avg(c)
        from (select count(film_id) c from film group by release_year) as a;

+----+-------------+------------+------------+------+---------------+------+---------+------+------+----------+-----------------+
| id | select_type | table      | partitions | type | possible_keys | key  | key_len | ref  | rows | filtered | Extra           |
+----+-------------+------------+------------+------+---------------+------+---------+------+------+----------+-----------------+
|  1 | PRIMARY     | <derived2> | NULL       | ALL  | NULL          | NULL | NULL    | NULL | 1000 |   100.00 | NULL            |
|  2 | DERIVED     | film       | NULL       | ALL  | NULL          | NULL | NULL    | NULL | 1000 |   100.00 | Using temporary |
+----+-------------+------------+------------+------+---------------+------+---------+------+------+----------+-----------------+
```

- UNION

`UNION`查询中第二个之后（包括第二个）的查询，第一个查询被标为`PRIMARY`。

- UNION RESULT

表示从MySQL为`UNION`查询创建的匿名临时表中检索结果。

## table

该列表示正在访问的表。通常该列显示的是表名，但是对于`DERIVED`和`UNION RESULT`查询就有些特殊。对于派生表该列显示的格式是`<derivedN>`，这里的`N`其实是子查询的`id`，比如在上面的例子中，有一个表名是`<derived2>`；对于`UNION RESULT`查询该列显示的格式是`<unionA,B,C...>`，这里的`A`、`B`等字母也是子查询的`id`，从小到达排列，在上面的例子有一个表名是`<union1,2>`。

## partitions

查询命中的分区表情况。

## type

`type`列表示表的连接类型，主要有以下几种类型，

- ALL

全表扫描，这是最坏的情况。

- index

`index`在两种情况下会出现，一种是查询用了覆盖索引，只需要扫描索引中的数据，而不需要扫描原表的数据。索引中的数据通常只是原表中的几列，数据量相比原表少了很多，可以显著减少IO操作。当使用覆盖索引时`Extra`列会显示`Using index`。

```sql
explain select rental_date from rental;

+----+-------------+--------+------------+-------+---------------+-------------+---------+------+-------+----------+-------------+
| id | select_type | table  | partitions | type  | possible_keys | key         | key_len | ref  | rows  | filtered | Extra       |
+----+-------------+--------+------------+-------+---------------+-------------+---------+------+-------+----------+-------------+
|  1 | SIMPLE      | rental | NULL       | index | NULL          | rental_date | 10      | NULL | 16008 |   100.00 | Using index |
+----+-------------+--------+------------+-------+---------------+-------------+---------+------+-------+----------+-------------+
```

另一种情况出现在全表扫描，区别是按索引而不是按行的次序进行扫描，优点是避免了排序。

```sql
explain select * from actor order by actor_id;

+----+-------------+-------+------------+-------+---------------+---------+---------+------+------+----------+-------+
| id | select_type | table | partitions | type  | possible_keys | key     | key_len | ref  | rows | filtered | Extra |
+----+-------------+-------+------------+-------+---------------+---------+---------+------+------+----------+-------+
|  1 | SIMPLE      | actor | NULL       | index | NULL          | PRIMARY | 2       | NULL |  200 |   100.00 | NULL  |
+----+-------------+-------+------------+-------+---------------+---------+---------+------+------+----------+-------+
```

- range

索引范围扫描，常见于使用`=`，`<>`，`>`，`>=`，`IS NULL`，`BETWEEN`，`IN`，`like`等运算符的查询中。

```sql
explain select *
        from actor
        where last_name > 'Y';

+----+-------------+-------+------------+-------+---------------------+---------------------+---------+------+------+----------+-----------------------+
| id | select_type | table | partitions | type  | possible_keys       | key                 | key_len | ref  | rows | filtered | Extra                 |
+----+-------------+-------+------------+-------+---------------------+---------------------+---------+------+------+----------+-----------------------+
|  1 | SIMPLE      | actor | NULL       | range | idx_actor_last_name | idx_actor_last_name | 137     | NULL |    3 |   100.00 | Using index condition |
+----+-------------+-------+------------+-------+---------------------+---------------------+---------+------+------+----------+-----------------------+
```

- ref

当两张表连接时，如果第二张表中的列的索引不是唯一索引或者主键，那么`type`列就是`ref`。比如下面的语句中第二张表`rental`的`staff_id`列上的索引是普通索引，不是`rental`表的主键或唯一索引，这时`type`就是`ref`。简单来说就是第一张表的某一行数据在第二张表中可能匹配到多行数据。

```sql
explain select staff.staff_id
        from staff
               left join rental on staff.staff_id = rental.staff_id;

+----+-------------+--------+------------+-------+-----------------+-----------------+---------+-----------------------+------+----------+-------------+
| id | select_type | table  | partitions | type  | possible_keys   | key             | key_len | ref                   | rows | filtered | Extra       |
+----+-------------+--------+------------+-------+-----------------+-----------------+---------+-----------------------+------+----------+-------------+
|  1 | SIMPLE      | staff  | NULL       | index | NULL            | idx_fk_store_id | 1       | NULL                  |    2 |   100.00 | Using index |
|  1 | SIMPLE      | rental | NULL       | ref   | idx_fk_staff_id | idx_fk_staff_id | 1       | sakila.staff.staff_id | 8004 |   100.00 | Using index |
+----+-------------+--------+------------+-------+-----------------+-----------------+---------+-----------------------+------+----------+-------------+
```

- eq_ref

和`ref`相反，当两张表连接时，如果第二张表中的列的索引是唯一索引或者主键，那么`type`列就是`eq_ref`。下面的语句中第二张表`store`表的`manager_staff_id`列上建立了唯一索引，因此改列的值是唯一的，所以此时`type`列是`eq_ref`。简单来说就是第一张表的某一行数据在第二张表中只能匹配到一行数据。

```sql
explain select *
        from staff,
             store
        where staff.staff_id = store.manager_staff_id;

+----+-------------+-------+------------+--------+--------------------+---------+---------+-------------------------------+------+----------+-------+
| id | select_type | table | partitions | type   | possible_keys      | key     | key_len | ref                           | rows | filtered | Extra |
+----+-------------+-------+------------+--------+--------------------+---------+---------+-------------------------------+------+----------+-------+
|  1 | SIMPLE      | store | NULL       | ALL    | idx_unique_manager | NULL    | NULL    | NULL                          |    2 |   100.00 | NULL  |
|  1 | SIMPLE      | staff | NULL       | eq_ref | PRIMARY            | PRIMARY | 1       | sakila.store.manager_staff_id |    1 |   100.00 | NULL  |
+----+-------------+-------+------------+--------+--------------------+---------+---------+-------------------------------+------+----------+-------+
```

- const

查询的列是主键或建立了唯一索引，查询时最多只返回一行数据。

```sql
explain select * from film where film_id = 3;

+----+-------------+-------+------------+-------+---------------+---------+---------+-------+------+----------+-------+
| id | select_type | table | partitions | type  | possible_keys | key     | key_len | ref   | rows | filtered | Extra |
+----+-------------+-------+------------+-------+---------------+---------+---------+-------+------+----------+-------+
|  1 | SIMPLE      | film  | NULL       | const | PRIMARY       | PRIMARY | 2       | const |    1 |   100.00 | NULL  |
+----+-------------+-------+------------+-------+---------------+---------+---------+-------+------+----------+-------+
```

- system

表中只有一行数据。

## possible_keys

查询可以使用的索引。

## key

查询时真正用到的索引。如果索引没有在`possible_keys`列中出现，但是却出现在了`key`这一列中，那么可能用到了覆盖索引。

## key_len

索引的字节数，计算方式是把索引中用到的每一列的最大字节数相加。比如下面的查询中主键`actor_id`的类型是`smallint`，该查询用到了主键索引，因此`key_len`的值就是`actor_id`列的字节数，也就是`smallint`类型的字节数2。

```sql
explain select * from actor where actor_id = 18;
```

如果是字符串，比如`char(20)`，在UTF-8编码下，每个字符最多占3个字节，因此它的字节数就是20*3。

对于一个包含多个列的联合索引，查询语句不一定会用到索引中所有的列，没有用到的列不会计算到`key_len`中。此外，`key_len`只会计算`where`条件中用到的索引的长度，对于`order by`和`group by`语句，即使用到了索引也不会计算到`key_len`中。

计算`key_len`时需要注意，列是否允许`NULL`以及字符编码的不同等因素都会引起计算结果的变化。

## ref

对于`JOIN`查询`ref`列显示的是关联字段，比如下面查询语句中`JOIN`后面的`film_actor`表的`film_id`关联了`film`表的`film_id`字段（即ref列的`sakila.film.film_id`）。

```sql
explain select *
        from film
               left join film_actor on film.film_id = film_actor.film_id;

+----+-------------+------------+------------+------+----------------+----------------+---------+---------------------+------+----------+-------+
| id | select_type | table      | partitions | type | possible_keys  | key            | key_len | ref                 | rows | filtered | Extra |
+----+-------------+------------+------------+------+----------------+----------------+---------+---------------------+------+----------+-------+
|  1 | SIMPLE      | film       | NULL       | ALL  | NULL           | NULL           | NULL    | NULL                | 1000 |   100.00 | NULL  |
|  1 | SIMPLE      | film_actor | NULL       | ref  | idx_fk_film_id | idx_fk_film_id | 2       | sakila.film.film_id |    5 |   100.00 | NULL  |
```

对于`type`类型是`const`的查询，其`ref`列也为`const`。

```sql
explain select * from film where film_id = 3;

+----+-------------+-------+------------+-------+---------------+---------+---------+-------+------+----------+-------+
| id | select_type | table | partitions | type  | possible_keys | key     | key_len | ref   | rows | filtered | Extra |
+----+-------------+-------+------------+-------+---------------+---------+---------+-------+------+----------+-------+
|  1 | SIMPLE      | film  | NULL       | const | PRIMARY       | PRIMARY | 2       | const |    1 |   100.00 | NULL  |
+----+-------------+-------+------------+-------+---------------+---------+---------+-------+------+----------+-------+
```

## rows

扫描行数，这是一个估算值。

## filtered

表示符合条件的数据的行数占需要扫描的行数（也就是`rows`列的值）的百分比，比如该列值为40，则表示在扫描的数据行中估计有40%的数据满足查询条件。
`filtered`只有在`type`列是`ALL`和`index`时才有效，在其它情况下`filtered`的值总是100，这是因为在其它情况下符合查询条件的数据行数通常等于扫描的行数。

## Extra

该列显示了一些额外的信息，常见的有以下几项。

- Using index

查询使用了覆盖索引，从索引中就可以查询出所需要的数据而不需要读取原表。

- Using where

表示MySQL服务器在存储引擎返回结果后再次进行过滤。

- Using temporary

查询用到了临时表。

- Using filesort

表示无法利用索引对查询结果进行排序。

## 参考

1. [《MySQL性能优化神器Explain使用分析》](https://segmentfault.com/a/1190000008131735)
2. [《What does eq_ref and ref types mean in MySQL explain》](https://stackoverflow.com/questions/4508055/what-does-eq-ref-and-ref-types-mean-in-mysql-explain)
