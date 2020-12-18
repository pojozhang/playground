# 系统数据库

- mysql: 存储MySQL用户账户信息、权限信息、存储过程和事件的定义信息、运行过程中产生的日志信息、帮助信息以及时区信息等。

- information_schema: 存储其他数据库的元数据，如其他数据库拥有的表、索引、视图、触发器等。

- performance_schema: 记录运行过程中的一些状态信息，比如最近执行的语句、执行语句时各个阶段的耗时、内存使用情况等信息，通过它可以监控MySQL的性能。

- sys: 通过视图把information_schema和performance_schema结合起来，以便开发者更容易了解性能状况。

## 参考

1. 《MySQL是怎样运行的》
