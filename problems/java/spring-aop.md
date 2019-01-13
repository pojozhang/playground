# Spring Aop

## 概念

### 切点（Pointcut）

切点是指切入的地点，可以是一个或多个符合指定条件的方法。切点对应的注解是`@Pointcut`，比如下面的切点定义了在执行`EchoService`的`echo(String)`方法时去执行我们自定义的通知。

```java
@Pointcut("execution(* playground.spring.service.EchoService.echo(String))")
private void echoPointcut() {
}
```

### 通知（Advice）

通知就是在切点处执行的代码，有以下5种通知。

- 前置通知

在切点指定的目标方法之前执行，对应的注解是`@Before`。

```java
@Before("echoPointcut()")
void before(JoinPoint joinPoint) {
}
```

- 后置通知

在切点指定的目标方法之后执行，对应的注解是`@AfterReturning`。

```java
@AfterReturning(value = "echoPointcut()")
void afterRunning(JoinPoint joinPoint) {
}
```

- 异常通知

在切点抛出异常后执行，对应的注解是`@AfterThrowing`。

```java
@AfterThrowing(value = "echoPointcut()")
void afterThrowing(JoinPoint joinPoint) {
}
```

- 最终通知

切点正常返回后或抛出异常后执行，对应的注解是`@After`。

```java
@After(value = "echoPointcut()")
void after(JoinPoint joinPoint) {
}
```

- 环绕通知

在切点调用之前和调用之后执行自定义的行为，对应的注解是`@Around`。

```java
@Around(value = "echoPointcut()")
void around(ProceedingJoinPoint joinPoint) throws Throwable {
    joinPoint.proceed();
}
```

#### 执行顺序

同一个切面点中不同类型的通知的执行顺序分为两种情况。

1. 正常执行。

`Around`->`Before`->`Around`->`After`->`AfterReturning`。

2. 异常执行。

`Around`->`Before`->`After`->`AfterThrowing`。

### 连接点（JoinPoint）

### 切面（Aspect）

切面是切点和通知的集合。
