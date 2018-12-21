# 锁 ![版本](https://img.shields.io/badge/java-11-red.svg)

## ReentrantLock

`ReentrantLock`内部委托`Sync`类进行加锁和解锁的操作，`Sync`是`ReentrantLock`的内部抽象类，继承自`AbstractQueuedSynchronizer`。

```java
private final Sync sync;
```

```java
public ReentrantLock() {
    sync = new NonfairSync();
}

public ReentrantLock(boolean fair) {
    sync = fair ? new FairSync() : new NonfairSync();
}
```

### 公平锁

### 非公平锁
