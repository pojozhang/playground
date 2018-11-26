# 线程

Windows和Linux版的JDK采用一对一的线程模型实现，即一条Java线程映射到一条系统线程中。

Java使用抢占式线程调度方式，每个线程的执行时间由系统来分配，线程本身不能决定线程切换的时机。

## 生命周期

通过枚举类`java.lang.Thread.State`，可以知道一个线程在一个生命周期中可以有以下6种状态。

- NEW

当一个`Thread`对象被创建时，它的状态就是`NEW`。

```java
System.out.println(new Thread().getState());
```

- RUNNABLE

当我们调用`start()`方法后，线程就从`NEW`转变成`RUNNABLE`，`RUNNABLE`的意思是就绪，线程可能已经在执行也可能正在等待被CPU调度。

```java
Thread thread = new Thread();
thread.start();
System.out.println(thread.getState());
```

- BLOCKED

当线程试图去获得一把锁而这把锁已经被其它线程占用时，线程状态变为阻塞态`BLOCKED`，在这种状态下线程不会被分配CPU执行时间。下面两个线程对象同时争抢一把锁，有一个线程的状态会打印出`BLOCKED`。

```java
Object lock = new Object();
Thread thread1 = new Thread(() -> {
    synchronized (lock) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {}
    }
});

Thread thread2 = new Thread(() -> {
    synchronized (lock) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {}
    }
});

thread1.start();
thread2.start();

for (; ; ) {
    System.out.println(thread1.getState());
    System.out.println(thread2.getState());
}
```

- WAITING

等待状态是指当前线程正在等待其它的线程采取某些操作，在这种状态下线程不会被分配CPU执行时间。比如当我们调用`wait()`方法等待被另一个线程唤醒时，该线程就出于`WAITING`状态，它和`BLOCKED`最大的区别是`WAITING`是主动去等待，而`BLOCKED`是被动的。

```java
Object obj = new Object();
Thread thread1 = new Thread(() -> {
    try {
        synchronized (obj) {
            obj.wait();
        }
    } catch (InterruptedException e) {}
});

thread1.start();

for (; ; ) {
    System.out.println(thread1.getState());
}
```

- TIMED_WAITING

和`WAITING`类似，只是设置了超时时间，在这种状态下线程不会被分配CPU执行时间。我们对上面的例子进行改造，使用`wait()`方法的一个重载版本，传入超时时间5秒，这时线程就处于`TIMED_WAITING`状态。

```java
Object obj = new Object();
Thread thread1 = new Thread(() -> {
    try {
        synchronized (obj) {
            obj.wait(5000);
        }
    } catch (InterruptedException e) {}
});

thread1.start();

for (; ; ) {
    System.out.println(thread1.getState());
}
```

- TERMINATED

线程终止，无论是异常退出还是正常执行完毕。

## join()

等待线程终止。以下代码在主线程中启动一个子线程并等它执行完成。

```java
public static void main(String[] args) throws InterruptedException {
    Thread thread = new Thread(() -> {
        for (int i = 0; i < 3; i++) {
            try {
                System.out.println("hello");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    });
    thread.start();
    thread.join();
}
```
