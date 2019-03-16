# CountDownLatch

![版本](https://img.shields.io/badge/java-11-blue.svg)

`CountDownLatch`是基于[AQS](aqs.md)实现的，我们从它的构造方法看起。

当我们通过构造方法传入一个数值时，`CountDownLatch`会创建一个`Sync`对象。

```java
public CountDownLatch(int count) {
    if (count < 0) throw new IllegalArgumentException("count < 0");
    this.sync = new Sync(count);
}
```

`Sync`是一个内部类，继承自`AbstractQueuedSynchronizer`，它在构造方法中把传入的`count`参数赋值给AQS中的`state`字段。

```java
// java.util.concurrent.CountDownLatch.Sync#Sync
Sync(int count) {
    setState(count);
}
```

当我们调用`countDown()`方法时会委托给`sync`对象。

```java
// java.util.concurrent.CountDownLatch#countDown
public void countDown() {
    sync.releaseShared(1);
}
```

`releaseShared()`方法是定义在AQS中的模板方法，其中`tryReleaseShared()`方法需要子类自己实现。

```java
// java.util.concurrent.locks.AbstractQueuedSynchronizer#releaseShared
public final boolean releaseShared(int arg) {
    if (tryReleaseShared(arg)) {
        doReleaseShared();
        return true;
    }
    return false;
}
```

以下是`Sync`实现的`tryReleaseShared()`方法，其作用是利用CAS操作原子性的减去`state`字段的值，当第一次成功减为0时返回`true`，否则返回`false`。返回`true`表示需要唤醒那些调用`CountDownLatch`的`await()`方法后正在等待中的线程。

```java
// java.util.concurrent.CountDownLatch.Sync#tryReleaseShared
protected boolean tryReleaseShared(int releases) {
    // Decrement count; signal when transition to zero
    for (;;) {
        int c = getState();
        if (c == 0)
            return false;
        int nextc = c - 1;
        if (compareAndSetState(c, nextc))
            return nextc == 0;
    }
}
```

下面我们来看`await()`方法，同样也是委托给`sync`对象。`acquireSharedInterruptibly()`方法是定义在AQS中的模板方法，`Sync`类实现了`tryAcquireShared()`方法，其作用是当`state`字段减为0是返回1，否则返回-1。当返回1时线程不会被阻塞，`await()`方法会直接返回；当返回-1时当前线程会被放入AQS的等待队列并挂起。

```java
// java.util.concurrent.CountDownLatch#await()
public void await() throws InterruptedException {
    sync.acquireSharedInterruptibly(1);
}

// java.util.concurrent.locks.AbstractQueuedSynchronizer#acquireSharedInterruptibly
public final void acquireSharedInterruptibly(int arg)
        throws InterruptedException {
    if (Thread.interrupted())
        throw new InterruptedException();
    if (tryAcquireShared(arg) < 0)
        doAcquireSharedInterruptibly(arg);
}

// java.util.concurrent.CountDownLatch.Sync#tryAcquireShared
protected int tryAcquireShared(int acquires) {
    return (getState() == 0) ? 1 : -1;
}
```

当`countDown()`方法将`state`减到0后，接下来会调用AQS的`doReleaseShared()`方法唤醒等待中的线程，由于这里用的是AQS的共享模式，因此当一个线程被唤醒后它会继续唤醒下一个线程，不断递归下去，直到所有线程都被唤醒。
