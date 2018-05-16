package playground.design.singleton;

/**
 * 懒汉模式
 * <p>
 * 缺点: 线程不安全，可能在内存中存在多个实例
 * <p>
 * 优点: 简单，在不被调用的情况下，可以避免不必要的内存花费
 */
public class LazySingleton {

    private static LazySingleton instance;

    private LazySingleton() {
    }

    /**
     * 多个线程调用时，无法保证多个线程拿到的都是同一个对象
     *
     * @return instance
     */
    public static LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}
