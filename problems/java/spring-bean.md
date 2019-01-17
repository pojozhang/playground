# Spring Bean

## 作用域

- **singleton**

单例模式，只存在一个实例。这是默认的作用域，我们使用`@Bean`、`@Component`等注解声明的Bean都只有一个实例。

- **prototype**

每次从IOC容器中取Bean时都会返回一个新的实例。

我们先声明一个原型模式的Bean，如下所示。

```java
@Bean
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
PrototypeBean prototypeBean() {
    return new PrototypeBean();
}
```

然后通过应用上下文连续两次获取上面声明的Bean，经过比对可以发现这是两个对象。

```java
PrototypeBean bean1 = applicationContext.getBean(PrototypeBean.class);
PrototypeBean bean2 = applicationContext.getBean(PrototypeBean.class);
```

但是通常我们不会通过应用上下文的方式来获取Bean，而是用注入的方式，如下所示。

```java
@Autowired
private PrototypeBean prototypeBean;
```

你会发现每次都是同一个对象，原型作用域并没有生效。为了让作用域生效，我们需要在`@Scope`注解上指定代理模式，这样当Spring注入时会注入一个代理对象，每当我们调用代理对象的方法时，代理对象就会在内部创建新的对象后再进行方法调用。

```java
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
```

- **request**

对每一个Http请求创建一个新的对象。和原型作用域一样，当我们用注入的方式获取Bean时需要在`@Scope`作用域上指定代理模式，否则作用域不会生效。

```java
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
```

- **session**

对每一个会话创建一个新的对象。使用时同样需要在`@Scope`作用域上指定代理模式，否则作用域不会生效。

```java
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
```

## 参考

1. [《Injecting Prototype Beans into a Singleton Instance in Spring》](https://www.baeldung.com/spring-inject-prototype-bean-into-singleton)
