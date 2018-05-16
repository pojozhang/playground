package playground.design.singleton;

/**
 * 枚举单例
 * 这种方式是Effective Java作者Josh Bloch,它不仅能避免多线程同步问题，而且还能防止反序列化重新创建新的对象。
 *
 * 1. since 1.5
 * 2. 绝对防止多次实例化，即使在面对复杂的序列化或者反射攻击的时候
 *
 */
public enum  EnumSingleton {

    INSTANCE;

    private int i = 100;

    public int getI() {
        return i;
    }
}
