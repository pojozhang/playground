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

通知的执行点，包含运行时的一些信息，如方法名，方法的参数等信息，在Spring中连接点对应的抽象是`JoinPoint`接口。

### 切面（Aspect）

切面是切点和通知的集合。

## 原理

Spring Aop中用到的注释均来自AspectJ框架。AspectJ是一个AOP框架，它支持把我们在通知中自定义的行为编译到源码中，从而实现AOP的功能，但是由于需要特定的编译器配合，靠`javac`无法实现，因此Spring Aop并没有直接使用AspectJ实现AOP。

Spring Aop采用动态代理的方式实现AOP。当Spring启动后，所有被Spring托管的对象实例化之前都会检查是否有针对该对象的通知，如果有，那么我们需要创建对象的代理。

```java
// org.springframework.aop.framework.DefaultAopProxyFactory#createAopProxy
public AopProxy createAopProxy(AdvisedSupport config) throws AopConfigException {
    if (config.isOptimize() || config.isProxyTargetClass() || hasNoUserSuppliedProxyInterfaces(config)) {
        Class<?> targetClass = config.getTargetClass();
        if (targetClass == null) {
            throw new AopConfigException("TargetSource cannot determine target class: " +
                    "Either an interface or a target is required for proxy creation.");
        }
        if (targetClass.isInterface() || Proxy.isProxyClass(targetClass)) {
            return new JdkDynamicAopProxy(config);
        }
        return new ObjenesisCglibAopProxy(config);
    }
    else {
        return new JdkDynamicAopProxy(config);
    }
}
```

## 参考

1. [《spring源码剖析（六）AOP实现原理剖析》](https://blog.csdn.net/fighterandknight/article/details/51209822)
