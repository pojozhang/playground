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


## possible_keys

查询可以使用的索引。

## key

## key_len

## ref

## rows

估算的扫描行数。

## filtered



## Extra
