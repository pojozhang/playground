# 锁

![版本](https://img.shields.io/badge/java-11-blue.svg)

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

`ReentrantLock`通过调用`Sync`的`acquire()`、`release()`方法进行加锁和解锁操作，这两个方法实际上是定义在基类`AQS`中的。

```java
// 加锁。
public void lock() {
    sync.acquire(1);
}

// 解锁。
public void unlock() {
    sync.release(1);
}
```

`acquire()`和`release()`方法用到了模板方法的设计模式，子类需要自己实现`tryAcquire()`方法来获取锁，实现`tryRelease()`方法来释放锁，`NonfairSync`和`FairSync`都有各自的实现。

```java
// java.util.concurrent.locks.AbstractQueuedSynchronizer#acquire
public final void acquire(int arg) {
    if (!tryAcquire(arg) &&
        acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
        selfInterrupt();
}

// java.util.concurrent.locks.AbstractQueuedSynchronizer#release
public final boolean release(int arg) {
    if (tryRelease(arg)) {
        Node h = head;
        if (h != null && h.waitStatus != 0)
            unparkSuccessor(h);
        return true;
    }
    return false;
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
    // 获取state字段。
    // state字段定义在AQS类中，它的类型是private volatile int state。
    int c = getState();
    // 如果state是0，说明锁还没有被其它线程获取。
    // state相当于是可重入锁的计数器，当它等于0时表示锁没有被线程获取，如果一个线程多次获取同一把锁，state会不断增加，增加量就是参数acquires。
    if (c == 0) {
        // CAS设置state字段。
        if (compareAndSetState(0, acquires)) {
            // 如果设置成功说明获取到了锁，设置锁的拥有者为当前线程。
            setExclusiveOwnerThread(current);
            return true;
        }
    }
    // 当前线程已经是锁的拥有者。
    else if (current == getExclusiveOwnerThread()) {
        int nextc = c + acquires;
        // nextc小于0说明溢出了。
        if (nextc < 0)
            throw new Error("Maximum lock count exceeded");
        // 更新state。
        setState(nextc);
        return true;
    }

    // 锁已经被其它线程获取。
    return false;
}
```

`NonfairSync`类在获取锁的时候会直接用CAS操作去争抢锁，因此它是非公平的。

`NonfairSync`释放锁的逻辑定义在基类`Sync`中。

```java
// java.util.concurrent.locks.ReentrantLock.Sync#tryRelease
protected final boolean tryRelease(int releases) {
    int c = getState() - releases;
    // 如果当先线程不是锁的持有者，那么抛出异常。
    if (Thread.currentThread() != getExclusiveOwnerThread())
        throw new IllegalMonitorStateException();
    boolean free = false;
    // c等于0说明没有线程持有当前锁了。
    if (c == 0) {
        free = true;
        setExclusiveOwnerThread(null);
    }
    setState(c);
    return free;
}
```

### 公平锁

`FairSync`实现了公平锁的逻辑。

```java
// java.util.concurrent.locks.ReentrantLock.FairSync#tryAcquire
protected final boolean tryAcquire(int acquires) {
    final Thread current = Thread.currentThread();
    int c = getState();
    // state等于0，说明锁还没有被线程获取。
    if (c == 0) {
        // 判断AQS的等待队列中是否有正在等待的线程。
        // 如果没有正在等待获取锁的线程，那么当前线程用CAS获取锁。
        if (!hasQueuedPredecessors() &&
            compareAndSetState(0, acquires)) {
            setExclusiveOwnerThread(current);
            return true;
        }
    }
    // 当前线程已经是锁的拥有者。
    else if (current == getExclusiveOwnerThread()) {
        int nextc = c + acquires;
        // 溢出判断。
        if (nextc < 0)
            throw new Error("Maximum lock count exceeded");
        // 更新state。
        setState(nextc);
        return true;
    }
    return false;
}

// java.util.concurrent.locks.AbstractQueuedSynchronizer#hasQueuedPredecessors
public final boolean hasQueuedPredecessors() {
    // head和tail是AQS中等待队列的首位节点。
    // head是一个空节点，因此head.next才是等待获取锁的第一个节点。
    Node t = tail;
    Node h = head;
    Node s;
    return h != t &&
        ((s = h.next) == null || s.thread != Thread.currentThread());
}
```

`FairSync`释放锁的逻辑和`NonfairSync`类一样，都是调用定义在基类`Sync`中的`tryRelease()`方法。
