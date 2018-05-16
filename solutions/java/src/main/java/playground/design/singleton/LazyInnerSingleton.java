package playground.design.singleton;

/**
 * 懒汉模式 - 内部类实现
 * <p>
 * 借用 JSL 在对象初始化时，不是并行的特点，在没有调用 getInstance 时，不会初始化内部对象
 * <p>
 * 优点：线程安全，性能优良，不会占用可能不使用的内存，帅 ：P
 * 缺点：实现起来较为复杂
 */
public class LazyInnerSingleton {

    private LazyInnerSingleton() {
    }

    public static LazyInnerSingleton getInstance() {
        return Singleton.instance;
    }

    private static final class Singleton {
        private static final LazyInnerSingleton instance = new LazyInnerSingleton();
    }
}
