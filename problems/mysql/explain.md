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

## select_type

## table

## partitions

## type

## possible_keys

## key

## key_len

## ref

## rows

## filtered

## Extra
