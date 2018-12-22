# 锁

![版本](https://img.shields.io/badge/java-11-red.svg)

JDK中锁的实现基于[AQS](aqs.md)框架，因此在看本文前需要对AQS有一定的了解。

## ReentrantLock

`ReentrantLock`内部委托`Sync`类进行加锁和解锁的操作，`Sync`是`ReentrantLock`的内部抽象类，继承自`AbstractQueuedSynchronizer`。

```java
private final Sync sync;
```

`ReentrantLock`锁有两种模式，一种是公平锁，对应的实现是`FairSync`，另一种是非公平锁，对应的实现是`NonfairSync`，`NonfairSync`和`FairSync`都是`Sync`类的子类，无参构造器默认是非公平锁。

```java
// 非公平锁。
public ReentrantLock() {
    sync = new NonfairSync();
}

// 通过参数决定锁的公平性。
public ReentrantLock(boolean fair) {
    sync = fair ? new FairSync() : new NonfairSync();
}
```

`ReentrantLock`通过调用`Sync`的`acquire()`方法进行加锁，该方法实际上是定义在基类`AQS`中的。

```java
public void lock() {
    sync.acquire(1);
}
```

`acquire()`方法用到了模板方法的设计模式，子类需要自己实现`tryAcquire()`方法来获取锁，`NonfairSync`和`FairSync`都有各自的实现。

```java
// java.util.concurrent.locks.AbstractQueuedSynchronizer#acquire
public final void acquire(int arg) {
    if (!tryAcquire(arg) &&
        acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
        selfInterrupt();
}
```

### 非公平锁

`NonfairSync`实现了非公平锁的逻辑，是我们常用的模式。其`tryAcquire()`方法通过调用基类`Sync`中的`nonfairTryAcquire()`方法来获取锁。

```java
// java.util.concurrent.locks.ReentrantLock.NonfairSync#tryAcquire
protected final boolean tryAcquire(int acquires) {
    return nonfairTryAcquire(acquires);
}

// java.util.concurrent.locks.ReentrantLock.Sync#nonfairTryAcquire
final boolean nonfairTryAcquire(int acquires) {
    final Thread current = Thread.currentThread();
    int c = getState();
    if (c == 0) {
        if (compareAndSetState(0, acquires)) {
            setExclusiveOwnerThread(current);
            return true;
        }
    }
    else if (current == getExclusiveOwnerThread()) {
        int nextc = c + acquires;
        if (nextc < 0) // overflow
            throw new Error("Maximum lock count exceeded");
        setState(nextc);
        return true;
    }
    return false;
}
```


### 公平锁

`FairSync`实现了公平锁的逻辑。

```java
// java.util.concurrent.locks.ReentrantLock.FairSync#tryAcquire
protected final boolean tryAcquire(int acquires) {
    final Thread current = Thread.currentThread();
    int c = getState();
    if (c == 0) {
        if (!hasQueuedPredecessors() &&
            compareAndSetState(0, acquires)) {
            setExclusiveOwnerThread(current);
            return true;
        }
    }
    else if (current == getExclusiveOwnerThread()) {
        int nextc = c + acquires;
        if (nextc < 0)
            throw new Error("Maximum lock count exceeded");
        setState(nextc);
        return true;
    }
    return false;
}
```
