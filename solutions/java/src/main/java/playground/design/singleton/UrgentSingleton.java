package playground.design.singleton;

/**
 * 饿汉模式 - 急需模式
 * <p>
 * 利用JSL性质，在程序启动的时候，加载单例模式到内存中
 * <p>
 * 优点：简单，线程安全
 * 缺点：如果这个单例模式从未被吊用过，可能就会浪费这些内存
 */
public class UrgentSingleton {

    private static final UrgentSingleton instance = new UrgentSingleton();

    private UrgentSingleton() {
    }

    public static UrgentSingleton getInstance() {
        return instance;
    }
}
