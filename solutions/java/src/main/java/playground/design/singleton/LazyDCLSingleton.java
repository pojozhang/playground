package playground.design.singleton;

import java.util.Random;

/**
 * 懒汉DCL模式
 * <p>
 * Double-Check-Locking (DCL)
 * <p>
 * 双重检查(DCL)模式，出现的原因，是因为同步模式的懒汉单例，性能不高，毕竟synchronized粒度过大
 *
 * @see LazySyncSingleton
 * 但是DCL也存在致命缺点，无法完全保证再多线程情况下，所获取到的实例的完整性
 * <p>
 * <p>
 * 程序的正常流程 Thread A { 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 }
 * <p>
 * 但是当前 Thread B 进来时，如果 Thread A 已经开始创建当里对象时，Thread B 可能拿到的是一个未创建完成的单例对象
 * 当Thread B 调用 7 时，没有拿到想要的 数据
 * <p>
 * <p>
 * 优点：减少了锁竞争
 * 缺点：无法完全保证线程安全
 */
public class LazyDCLSingleton {

    private static LazyDCLSingleton singleton;

    private int random;

    private LazyDCLSingleton() {
        this.random = new Random().nextInt() * 1000;        // 6
    }

    public static LazyDCLSingleton getInstance() {

        if (singleton == null) {                            // 1
            synchronized (LazyDCLSingleton.class) {           // 2
                if (singleton == null) {                    // 3
                    singleton = new LazyDCLSingleton();       // 4
                }
            }
        }
        return singleton;                                   // 5
    }

    public int getRandom() {
        return random;                                      // 7
    }
}
