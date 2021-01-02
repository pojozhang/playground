# 优化器

## 子查询优化

- IN子查询优化

```sql
select * from table1 where column in (select column from table2);
```

MySQL会把子查询（也就是括号中的部分）的结果写入到临时表中，并且会为这张临时表建立主键或唯一索引，从而在写入到临时表时达到数据去重的目的。通常MySQL会用Memory引擎建临时表并为其建立哈希索引，但是如果子查询的结果集非常大（超过了设定的阈值），那么就会转用基于磁盘的存储引擎来建立临时表，索引也会转变为用B+树建立。这个把子查询结果集中的记录保存到临时表的过程称为物化。

在物化后，优化器会把上面的查询语句转成内连接，然后对表的连接顺序进行评估，决定哪张表作为驱动表，哪张表作为被驱动表。

```sql
-- 这里的temp_table就是临时表。
select * from table1 inner join temp_table on table1.column = temp_table.column;
```

除了转成内连接外，针对特殊的情况优化器还会采用其他手段针对特定情况进行优化。

## 参考

1. 《MySQL是怎样运行的》
