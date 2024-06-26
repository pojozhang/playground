# 事务隔离级别

当多个事务并发执行时，由于不同的业务场景，存在不同的对数据一致性的需求。例如：一个查询语句必须查询到最新的数据，并且阻塞其他所有的写操作。但由于需求的多样性，以及不同的场景存在不同的性能要求，因此数据库提供了多种事务隔离界别来满足不同的数据一致性的需求。

对于数据一致性的需求经常遇到以下几个问题：

- 脏读：指**一个事务读到其他事务未提交的数据**。
- 不可重复读：指**一个事务中相同的查询在不同时间点得到了不同的结果**。
- 幻读：指**一个事务读取了某些符合条件的数据，另一个事务的写入改变了先前的查询结果**。

各个隔离级别解决问题的对应关系：

|          |  读未提交 | 读已提交 | 可重复读  | 串行化 |
|----------|----------|--------|----------|-------|
| 脏读      | 未解决    |  解决  |   解决    |   解决 |
| 不可重复读 | 未解决    | 未解决  |   解决    |  解决 |
| 幻读      | 未解决    | 未解决  | 未完全解决 |  解决 |

## 读已提交

当有以下需求时，需要使用读已提交级别防止脏读：

1. 事务需要更新多个对象（多个数据库表）。如果发生脏读，另一个事务可能会看到部分更新。
2. 如果事务发生中止，所有写操作都要回滚。如果发生脏读，其他事务可能会看到稍后被回滚的数据，后续操作的结果会变得难以预测。

## 可重复读

当业务场景不能容忍数据不一致时，需要使用可重复读，例如：

1. 数据备份：需要保证数据一致性，不能容忍新旧数据混合。
2. 分析查询：根据数据进行业务分析。
3. 完整性检查：检查业务数据是否符合预期。

## 可重复度读和幻读

在可重复读隔离级别下，并没有完全解决幻读的问题，具体是否存在幻读需要根据实际的使用场景判断。

当在写倾斜的场景下会出现幻读。写倾斜指事务首先查询数据，根据返回的结果作出某些决定，然后修改数据库，当事务提交时，先前查询的数据已经发生变化，导致执行决定的条件不成立。例如：先通过`SELECT`语句进行查询，如果数据不存在则用`INSERT`语句进行插入，但此时由于其他并发事务已经插入了相同的数据，因此会得到索引冲突的异常，这时就发生了幻读。

> - 快照读： 读取数据的历史版本，不会对数据加锁。例如：`SELECT`操作。
> - 当前读： 读取数据的最新版本，会对数据加锁。例如：`INSERT`、`UPDATE`、`DELETE`、`SELECT FOR UPDATE`、`SELECT LOCK IN SHARE MODE`操作。

在可重复度读的隔离级别下，如果事务中只使用了当前读，由于会对数据上锁（对不存在的数据上间隙锁），因此可以认为是解决了幻读的问题。

## 参考

1. [《MySQL到底有没有解决幻读问题？》](https://heapdump.cn/article/4547714)
