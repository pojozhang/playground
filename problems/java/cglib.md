# Cglib

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
    proxy.f();
}
```