# MVCC

MVCC(Multi-Version Concurrency Control)中文名多版本并发控制，在可重复读的隔离级别下，一个事务中同一个查询语句无论执行多少次其结果都跟事务开始时的查询结果一致，这就是通过MVCC实现的。

## 原理

对于每一行数据，MySQL会在其后面增加以下3列。

- `DB_TRX_ID`

事务ID，记录了最后一次对该行数据进行过修改的事务ID，修改操作包括增、删、改，其中删除操作会把改行标记为已删除，而不是直接物理删除。

- `DB_ROLL_PTR`

undo log

- `DB_ROW_ID`

行Id。

MVCC也称一致性非锁定读，因为当要读取的数据行上因为UPDATE或DELETE操作而被上锁时，读取操作不会等待锁释放。

## 参考

1. [《MySQL-InnoDB-MVCC多版本并发控制》](https://segmentfault.com/a/1190000012650596)
2. [《Mysql InnoDB引擎逻辑存储与MVVC实现》](https://www.meiwen.com.cn/subject/mjpqyftx.html)
