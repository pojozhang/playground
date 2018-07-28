# 锁

## 可重入锁

可重入指的是同一把锁可以被同一个线程多次获取。通常的实现是锁的内部有一个计数器，当一个线程获取锁时计数器就加1同时记录锁的拥有者，解锁时减1，计数器减为0时锁就被释放。

> [🌰 可重入锁](https://github.com/pojozhang/playground/blob/master/solutions/java/src/main/java/playground/interview/MyReentrantLock.java)

重入的一个好处是简化了面向对象并发代码的开发。

相反的，一把锁只能被线程获取一次，那么它就是不可重入锁。

> [🌰 不可重入锁](https://github.com/pojozhang/playground/blob/master/solutions/java/src/main/java/playground/interview/NonReentrantLock.java)