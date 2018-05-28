package playground.design.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理: JDK实现方式
 *
 * 仔细观察会发现，这段代码其实可以代理任意接口，当然，这种代理方式会为每一个被代理者添加的操作都是一样的
 *
 * PS:通过反射类Proxy和InvocationHandler回调接口实现的jdk动态代理，
 * 要求委托类必须实现一个接口，但事实上并不是所有类都有接口，
 * 对于没有实现接口的类，便无法使用该方方式实现动态代理。
 *
 * 优点：
 *  - 相对于静态代理，更加的灵活，并且对于任意接口都能代理
 *
 * 缺点:
 *  - 被代理的必须要继承接口，就如上面的PS中描述一样，但是并不是所有的类或者对象都会继承接口，造成了JDK动态代理的局限性
 *
 */
public class JDKDynamicProxy {


    public void show() {
        DynamicProxy dynamicProxy = new DynamicProxy(new Customer());
        RailWay customerProxy = (RailWay) dynamicProxy.getProxy();
        customerProxy.buyTickets();
    }

    /**
     * 动态代理处理类
     */
    class DynamicProxy implements InvocationHandler {

        Object target;

        DynamicProxy(Object target) {
            this.target = target;
        }

        Object getProxy() {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            Class<?>[] interfaces = target.getClass().getInterfaces();

            /**
             * @ ClassLoader loader,      - classloader
             * @ Class<?>[] interfaces,   - 代理的接口
             * @ InvocationHandler        - 代理处理器
             */
            return Proxy.newProxyInstance(loader, interfaces, this);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            System.out.println(" before : " + method.toGenericString());

            Object result = method.invoke(target, args);

            System.out.println(" end    : " + method.toGenericString());

            return result;
        }

    }


    /**
     * 被代理类
     */
    private class Customer implements RailWay {

        @Override
        public void buyTickets() {
            System.out.println("I want go home, So i try to buy a ticket for myself");
        }

    }

}
