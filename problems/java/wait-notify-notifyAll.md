# wait()、notify()和notifyAll()

本文中的示例代码可以在[这里](https://github.com/pojozhang/playground/blob/master/solutions/java/src/test/java/playground/interview/WaitNotifyTest.java)查看。

Java中使用`Object`类中的`wait()`、`notify()`和`notifyAll()`方法进行线程间通信，这三个方法都必须在同步块中执行。

```java
synchronized (this) {
    try {
        this.wait();
        // this.notify();
        // this.notifyAll();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}
```

当某个线程调用对象的`wait()`方法后会释放线程持有的锁并阻塞，直到被其它线程通过`notify()`或`notifyAll()`方法唤醒。`notify()`方法会随机唤醒一个阻塞的线程，而`notifyAll()`则会唤醒所有阻塞的线程。线程被唤醒后需要竞争对象的锁，一旦获取了锁就可以从`wait()`恢复，继续执行之后的代码。当线程退出同步块后，剩下被唤醒的线程会继续竞争对象的锁，直到所有线程执行完毕。
