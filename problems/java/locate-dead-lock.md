# 如何定位死锁

我们可以利用`jstack`工具探测发生死锁的线程。

例如下面这段代码会产生死锁，我们用`jstack`工具打印线程的快照。

```java
public static void main(String[] args) throws InterruptedException {
    Object lockA = new Object();
    Object lockB = new Object();

    Thread threadA = new Thread(() -> {
        synchronized (lockA) {
            try {
                Thread.sleep(500);
                synchronized (lockB) {
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });
    threadA.start();

    Thread threadB = new Thread(() -> {
        synchronized (lockB) {
            try {
                Thread.sleep(500);
                synchronized (lockA) {
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });
    threadB.start();

    threadA.join();
}
```

可以看到如下结果，线程0持有锁`0x00000006cfb61050`而等待锁`0x00000006cfb61060`被释放，线程1持有锁`0x00000006cfb61060`而等待锁`0x00000006cfb61050`被释放，因此形成了死锁。

```java
"Thread-0":
	at Test.lambda$main$0(Test.java:12)
	- waiting to lock <0x00000006cfb61060> (a java.lang.Object)
	- locked <0x00000006cfb61050> (a java.lang.Object)
	at Test$$Lambda$1/940060004.run(Unknown Source)
	at java.lang.Thread.run(java.base@10/Thread.java:844)
"Thread-1":
	at Test.lambda$main$1(Test.java:25)
	- waiting to lock <0x00000006cfb61050> (a java.lang.Object)
	- locked <0x00000006cfb61060> (a java.lang.Object)
	at Test$$Lambda$2/1595953398.run(Unknown Source)
	at java.lang.Thread.run(java.base@10/Thread.java:844)
```
