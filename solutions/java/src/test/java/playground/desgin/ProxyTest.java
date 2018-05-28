package playground.desgin;

import org.junit.jupiter.api.Test;
import playground.design.proxy.JDKDynamicProxy;

public class ProxyTest {

    private JDKDynamicProxy jdkDynamicProxy = new JDKDynamicProxy();

    @Test
    public void case00(){
        jdkDynamicProxy.show();
    }

}
