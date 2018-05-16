package playground.design.singleton;

/**
 * 内部枚举单例
 *
 * 和常规枚举类似，但是这样主要是利用了枚举的加载机制
 *
 */
public class InnerEnumSingleton {

    private InnerEnumSingleton() {}

    private static InnerEnumSingleton getInstance() {
        return Singleton.HELPER.getInstance();
    }

    private enum Singleton {

        HELPER;

        private InnerEnumSingleton enumSingleton;

        Singleton() {
            enumSingleton = new InnerEnumSingleton();
        }

        private InnerEnumSingleton getInstance() {
            return enumSingleton;
        }

    }
}
