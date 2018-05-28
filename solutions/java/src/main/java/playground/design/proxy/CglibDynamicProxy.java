package playground.design.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 动态代理：CGLIB模式
 * <p>
 * PS:
 * - the target class must provide a default constructor ???? 早起版本，目前使用2.0.2 未发现这个问题
 * - 被代理类不能是内部类 ____ 别问我是怎么知道，你可以自己实验
 *
 * 优点：
 *  - 不是接口一样代理
 *
 * 缺点
 *  - 速度一般，CGLIB 是使用ASM直接字节码进行操作，生成代理类速度一般
 */
public class CglibDynamicProxy {

    public void show() {
        DynamicProxy dynamicProxy = new DynamicProxy();
        Person person = (Person) dynamicProxy.getProxy(new Person());
        person.sayHi();
    }

    class DynamicProxy implements MethodInterceptor {

        Object target;

        Object getProxy(Object target) {
            this.target = target;
            //增强器，动态代码生成器
            Enhancer enhancer = new Enhancer();
            //回调方法
            enhancer.setCallback(this);
            //设置生成类的父类类型
            enhancer.setSuperclass(target.getClass());
            //动态生成字节码并返回代理对象
            return enhancer.create();
        }

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

            System.out.println(" before : " + method.toGenericString());

            Object result = methodProxy.invoke(target, objects);

            System.out.println(" end    : " + method.toGenericString());

            return result;
        }
    }

}
