package playground.design.proxy;

/**
 * 静态代理: 被代理的对象为固定对象或者固定接口，可以通过hardcode的形式来完成代理模式编程，我们称这种代理方式为静态代理
 *
 * PS: 本文中的例子，是在当前代理类中创建了被代理对象，当然你也可以将被代理者通过参数传递进入代理类，但是本例子如要是侧重那些，被代理者是你能直接访问到的情况
 *
 * 场景选择: 已知类的行为，但是这个行为不能满足你现在的需求（春运反正我是很难抢到票），可以选择用代理模式进行扩展
 *
 * 优点: 对于拒绝修改，支持扩展原则，代理是一种很好的实现
 * 缺点:
 */
public class StaticProxy {

    public void showProxy() {
        new Scalper().buyTickets();
    }

    private class Scalper implements RailWay {

        @Override
        public void buyTickets() {
            RailWay customer = findWhoWantTickets();
            System.out.println("I Don't go home, But i can help u");
            customer.buyTickets();
        }

        private RailWay findWhoWantTickets() {
            return new Customer();
        }
    }

    private class Customer implements RailWay {

        @Override
        public void buyTickets() {
            System.out.println("I want go home, So i try to buy a ticket for myself");
        }
    }

    private interface RailWay {

        void buyTickets();

    }

}
