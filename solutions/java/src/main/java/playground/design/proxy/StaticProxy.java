package playground.design.proxy;

/**
 * 静态代理: 通过调用已知的对象方法，实现对原有的类的方法，进行扩展
 *
 * 场景选择: 已知类的行为，但是这个行为不能满足你现在的需求（春运反正我是很难抢到票），可以选择用代理模式进行扩展
 *
 * 优点: 对于拒绝修改，支持扩展原则，代理是一种很好的实现
 */
public class StaticProxy {

    public void showProxy() {
        new Scalper().buyTickets();
    }

    private class Scalper implements RailWay {

        @Override
        public void buyTickets() {
            Customer customer = findWhoWantTickets();
            System.out.println("I Don't go home, But i can help u");
            customer.buyTickets();
        }

        private Customer findWhoWantTickets() {
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
