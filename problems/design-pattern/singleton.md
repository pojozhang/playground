# 单例模式

## Java

### 懒汉模式

```java
public class Singleton {

    private static Singleton instance;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

- 缺点：线程不安全。

### 懒汉同步锁模式

```java
public class Singleton {

    private static Singleton instance;

    private Singleton() {
    }

    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

- 优点：线程安全。
- 缺点：每次都需要同步，性能差。

### 双重校验锁模式

```java
public class Singleton {

    private static volatile Singleton instance;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

- 优点：线程安全。
- 缺点：由于`singleton`是`volatile`变量，每次读取都要重新从主存中读取，因此效率不高。

### 懒汉内部类模式 ⭐️

```java
public class Singleton {

    private Singleton() {
    }

    public static Singleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static final class SingletonHolder {
        private static final Singleton INSTANCE = new Singleton();
    }
}
```

- 优点：线程安全。

### 饿汉模式 ⭐️

```java
public class Singleton {

    private static final Singleton INSTANCE = new Singleton();

    private Singleton() {
    }

    public static Singleton getInstance() {
        return INSTANCE;
    }
}
```

- 优点：线程安全。
- 缺点：不是懒加载，因此可能对象被初始化后永远都不会被使用。

### 枚举单例模式 ⭐️

```java
public enum Singleton {
    INSTANCE
}
```

- 优点：线程安全，实现简单，可以防止通过反射和序列化创建多个实例。
- 缺点：不具备普通类的某些特性，比如继承。

### 内部枚举模式

```java
public class Singleton {

    private Singleton() {
    }

    private static Singleton getInstance() {
        return SingletonHolder.HOLDER.getInstance();
    }

    private enum SingletonHolder {

        HOLDER;

        Singleton instance;

        SingletonHolder() {
            instance = new Singleton();
        }

        Singleton getInstance() {
            return instance;
        }
    }
}
```

- 优点：线程安全。
- 缺点：实现复杂。
