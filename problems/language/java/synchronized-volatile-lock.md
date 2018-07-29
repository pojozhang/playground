# synchronized、volatile 和 lock

## 可重入锁

可重入指的是同一把锁可以被同一个线程多次获取。通常的实现是锁的内部有一个计数器，当一个线程获取锁时计数器就加1同时记录锁的拥有者，解锁时减1，计数器减为0时锁就被释放。

> [例子：可重入锁](https://github.com/pojozhang/playground/blob/master/solutions/java/src/main/java/playground/interview/MyReentrantLock.java)

重入的一个好处是简化了并发代码的开发，下面看一个例子：
某个对象有`a()`，`b()`两个方法，它们都被`synchronized`修饰，因此在执行前都会先去获取该对象上的锁，如果锁是不可重入的，那么当`a()`方法中调用`b()`时发现锁已经被获取了，因此造成了死锁，而可重入锁避免了这个问题。

```java
private synchronized void a() {
    System.out.println("method A");
    b();
}

private synchronized void b() {
    System.out.println("method B");
}
```

相反的，一把锁只能被线程获取一次，那么它就是不可重入锁。

> [例子：不可重入锁](https://github.com/pojozhang/playground/blob/master/solutions/java/src/main/java/playground/interview/NonReentrantLock.java)

## synchronized

`synchronized`修饰符有三个作用：

1. 保证互斥性
2. 保证可见性
3. 保证顺序性

我们先看一段代码：

```java
public void test() {
    synchronized (this) {
        System.out.println("test");
    }
}
```

上述代码中，当有多个线程执行到`synchronized`这一句时，同一时刻只有一个线程能进入下面的代码块中，其余线程会一直阻塞直到之前的线程退出了代码块。

下面是对这段代码进行编译后再用`javap`工具打印出的字节码：

```java
public void test();
    Code:
       0: aload_0
       1: dup
       2: astore_1
       3: monitorenter
       4: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
       7: ldc           #3                  // String test
       9: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
      12: aload_1
      13: monitorexit
      14: goto          22
      17: astore_2
      18: aload_1
      19: monitorexit
      20: aload_2
      21: athrow
      22: return
```

我们可以看到`synchronized`代码块的首位加上了`monitorenter`和`monitorexit`指令。

在JVM中每个对象都有一个与之对应的`monitor`，当`monitor`被占用时对象就被上锁，并且这个`monitor`也有一个计数器，可以被同一个线程多次获取，因此我们可以看到它就是一个可重入锁。当JVM执行`monitorenter`指令时就会去尝试获取这把锁，当执行`monitorexit`指令时就把锁的计数器减去1，如果减为0，那么就释放。