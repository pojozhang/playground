# 事务

## 传播方式

Spring定义了以下几种事务的传播方式用来指定方法间互相调用时使用事务的不同策略，可以结合[示例代码](https://github.com/pojozhang/playground/blob/master/solutions/java/src/test/java/playground/spring/jpa/TransactionPropagationTest.java)对这些概念有一个直观的理解。

- REQUIRED

如果当前存在事务那么就用当前的事务，否则创建一个新的事务。

- REQUIRES_NEW

无论当前是否存在事务，总是创建一个新的事务。因为总是创建一个新的事务，因此当方法内抛出异常时，外部的事务不会回滚。

- MANDATORY

如果当前没有事务，那么抛出异常。

- SUPPORTS

如果当前存在事务那么就用当前的事务，否则不使用事务功能。在没有事务的情况下，如果方法内抛出异常，数据不会回滚。

- NOT_SUPPORTED

不使用事务。

- NEVER

不使用事务，如果当前存在事务则抛出异常。

- NESTED

如果当前存在事务，那么创建一个内嵌事务，否则和`REQUIRED`行为一致。

嵌套事务依赖于外部事务，只有当外部事务提交时才能提交内部事务，当外部事务回滚时内嵌事务也会回滚，当内嵌事务回滚时外部事务不会回滚。

> 目前只有`DataSourceTransactionManager`事务管理器支持内嵌事务，而该事务管理器不支持JPA和Hibernate，仅支持JDBC。当我们使用JPA修改数据库时，操作将不会生效，因此示例项目中采用JdbcTemplate进行写操作妈，读操作则不受影响。
