# Cglib

先看一个简单的Cglib动态代理的例子。

```java
class Proxied {

    public void f() {
        System.out.println("Proxied");
    }
}

class ProxyInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("Proxy");
        methodProxy.invokeSuper(o, objects);
        return o;
    }
}

public static void main(String[] args) {
    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(Proxied.class);
    enhancer.setCallback(new ProxyInterceptor());
    Proxied proxy = (Proxied) enhancer.create();
    proxy.f(); //此处打印“Proxy”，“Proxied”。
}
```

从使用方法来看，对比JDK自己的动态代理，Cglib的动态代理不需要被代理类实现接口，使用起来更加方便。下面我们来看下它的实现原理。

