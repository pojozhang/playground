# 事务

## 传播方式

Spring定义了以下几种事务的传播方式用来指定方法间互相调用时使用事务的不同策略，可以结合[示例代码](https://github.com/pojozhang/playground/blob/master/solutions/java/src/test/java/playground/spring/jpa/TransactionPropagationTest.java)对这些概念有一个直观的理解。

- REQUIRED

如果当前存在事务那么就用当前的事务，否则创建一个新的事务。

- REQUIRES_NEW

无论当前是否存在事务，总是创建一个新的事务。

- MANDATORY

如果当前没有事务，那么抛出异常。

- SUPPORTS



- NOT_SUPPORTED

- NEVER

- NESTED
