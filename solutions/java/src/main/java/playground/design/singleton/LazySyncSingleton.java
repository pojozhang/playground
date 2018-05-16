package playground.design.singleton;

/**
 * 懒汉模式 安全模式
 * <p>
 * 优点: 简单，线程安全，不被调用，不创建对象
 * <p>
 * 缺点:
 * - 多线程情况下，效率不是很理想
 * - 同步锁 加 在了 Static 方法上，是对当前CLass 内的所有资源进行加锁，如果当前实例中还存在其他静态方法也不能使用
 */
public class LazySyncSingleton {

    private static LazySyncSingleton instance = null;

    private LazySyncSingleton() {
    }

    public static synchronized LazySyncSingleton getInstance() {
        if (instance == null) {
            instance = new LazySyncSingleton();
        }
        return instance;
    }

}
