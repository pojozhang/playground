package playground.design.proxy;

import org.junit.jupiter.api.Test;
import playground.design.proxy.CglibDynamicProxy;
import playground.design.proxy.JDKDynamicProxy;

public class ProxyTest {

    private JDKDynamicProxy jdkDynamicProxy = new JDKDynamicProxy();

    private CglibDynamicProxy cglibDynamicProxy = new CglibDynamicProxy();

    @Test
    public void case00() {
        jdkDynamicProxy.show();
    }

    @Test
    public void case01() {
        cglibDynamicProxy.show();
    }


}
