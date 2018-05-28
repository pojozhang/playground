package playground.design.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKDynamicProxy {


    public void show() {
        ProxyInvocationHandler handler = new ProxyInvocationHandler(new Customer());
        RailWay customerProxy = (RailWay) handler.getProxy();
        customerProxy.buyTickets();
        customerProxy.sayHi();
    }

    /**
     * 动态代理处理类
     */
    class ProxyInvocationHandler implements InvocationHandler {

        private Object target;

        public ProxyInvocationHandler(Object target) {
            this.target = target;
        }

        public Object getProxy() {
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
    class Customer implements RailWay {

        @Override
        public void buyTickets() {
            System.out.println("I want go home, So i try to buy a ticket for myself");
        }

        @Override
        public void sayHi() {
            System.out.println("Hi~");
        }
    }


    /**
     * 代理接口
     */
    interface RailWay {

        void buyTickets();

        void sayHi();
    }
}
