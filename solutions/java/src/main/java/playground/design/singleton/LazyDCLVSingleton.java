package playground.design.singleton;

import java.util.Random;

/**
 * 懒汉DCL模式 + volatile
 * <p>
 * Double-Check-Locking (DCL) + volatile
 * <p>
 * 在对实例添加上 volatile 关键字之后，确保了 当前线程A 创建好 singleton 之后，
 * 在 Thread B 也可以读取当前 线程A改变后的数据
 * <p>
 * 优点：保证了多线程下的安全性
 * 缺点：由于volatile的性质，每次在操作这个对象时，都会查询一下MESI的状态
 *
 * @see LazyDCLSingleton
 */
public class LazyDCLVSingleton {

    private volatile static LazyDCLVSingleton singleton;

    private LazyDCLVSingleton() {}

    public static LazyDCLVSingleton getInstance() {

        if (singleton == null) {
            synchronized (LazyDCLVSingleton.class) {
                if (singleton == null) {
                    singleton = new LazyDCLVSingleton();
                }
            }
        }
        return singleton;
    }

}
