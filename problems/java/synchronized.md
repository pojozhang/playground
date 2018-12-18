# synchronized

在Java中我们经常使用`synchronized`关键字和`ReentrantLock`锁来实现线程安全，在深入理解它们的作用和原理之前，我们先要了解并发编程中的几个概念。

## 概念

### 线程同步

线程同步是指多个线程并发访问共享数据时，保证任意时刻该数据只能被一个线程进行访问，通常可以用互斥锁、信号量等方式实现同步。`synchronized`关键字和`ReentrantLock`锁就是用互斥的方法实现线程同步。

### 可重入锁

可重入指的是同一把锁可以被同一个线程多次获取。通常的实现是锁的内部有一个计数器，当一个线程获取锁时计数器就加1同时记录锁的拥有者，解锁时减1，计数器减为0时锁就被释放。

> [示例：可重入锁](https://github.com/pojozhang/playground/blob/master/solutions/java/src/main/java/playground/interview/MyReentrantLock.java)

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

> [示例：不可重入锁](https://github.com/pojozhang/playground/blob/master/solutions/java/src/main/java/playground/interview/NonReentrantLock.java)

## synchronized <a id="synchronized"></a>

`synchronized`修饰符有三个作用：

1. 保证原子性
2. 保证可见性
3. 保证有序性

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

我们可以看到`synchronized`代码块的首尾加上了`monitorenter`和`monitorexit`指令。

在JVM中每个对象都有一个与之对应的`monitor`，当对象的`monitor`被占用时就表示对象已经就其它线程占用，每个`monitor`拥有一个计数器，如果被同一个线程多次获取，那么就把计数器加上1，如果被线程释放，那么就把计数器减去1，相当于是一把可重入锁。当JVM执行`monitorenter`指令时就会去尝试获取这把锁，当执行`monitorexit`指令时就把锁的计数器减去1，如果减为0，那么就释放。

上面我们说只有对象才有对应的`monitor`，而基本类型不属于对象，因此以下代码是**不能**通过编译的。

```java
public void test() {
    int n = 0;
    synchronized (n) {
        System.out.println("test");
    }
}
```

### 源代码

现在我们看一下当JVM执行`monitorenter`指令时具体做了什么，你可以在[这里](https://github.com/unofficial-openjdk/openjdk/blob/jdk8u/jdk8u/hotspot/src/share/vm/interpreter/interpreterRuntime.cpp)找到源码。

```cpp
IRT_ENTRY_NO_ASYNC(void, InterpreterRuntime::monitorenter(JavaThread* thread, BasicObjectLock* elem))
#ifdef ASSERT
  thread->last_frame().interpreter_frame_verify_monitor(elem);
#endif
  if (PrintBiasedLockingStatistics) {
    Atomic::inc(BiasedLocking::slow_path_entry_count_addr());
  }
  Handle h_obj(thread, elem->obj());
  assert(Universe::heap()->is_in_reserved_or_null(h_obj()),
         "must be NULL or an object");
  if (UseBiasedLocking) {
    // Retry fast entry if bias is revoked to avoid unnecessary inflation
    ObjectSynchronizer::fast_enter(h_obj, elem->lock(), true, CHECK);
  } else {
    ObjectSynchronizer::slow_enter(h_obj, elem->lock(), CHECK);
  }
  assert(Universe::heap()->is_in_reserved_or_null(elem->obj()),
         "must be NULL or an object");
#ifdef ASSERT
  thread->last_frame().interpreter_frame_verify_monitor(elem);
#endif
IRT_END
```

你可以在[这里](https://github.com/unofficial-openjdk/openjdk/blob/jdk8u/jdk8u/hotspot/src/share/vm/runtime/synchronizer.cpp)找到源码。

```cpp
void ObjectSynchronizer::fast_enter(Handle obj, BasicLock* lock, bool attempt_rebias, TRAPS) {
 if (UseBiasedLocking) {
    if (!SafepointSynchronize::is_at_safepoint()) {
      BiasedLocking::Condition cond = BiasedLocking::revoke_and_rebias(obj, attempt_rebias, THREAD);
      if (cond == BiasedLocking::BIAS_REVOKED_AND_REBIASED) {
        return;
      }
    } else {
      assert(!attempt_rebias, "can not rebias toward VM thread");
      BiasedLocking::revoke_at_safepoint(obj);
    }
    assert(!obj->mark()->has_bias_pattern(), "biases should be revoked by now");
 }

 slow_enter (obj, lock, THREAD) ;
}
```

在HotSpot虚拟机中，`monitor`的实现是`ObjectMonitor`，可以在[这里](https://github.com/unofficial-openjdk/openjdk/blob/jdk8u/jdk8u/hotspot/src/share/vm/runtime/objectMonitor.hpp)看到源码，其数据结构如下：

```cpp
ObjectMonitor() {
    _header       = NULL;
    _count        = 0;
    _waiters      = 0,
    _recursions   = 0;
    _object       = NULL;
    _owner        = NULL;
    _WaitSet      = NULL;
    _WaitSetLock  = 0 ;
    _Responsible  = NULL ;
    _succ         = NULL ;
    _cxq          = NULL ;
    FreeNext      = NULL ;
    _EntryList    = NULL ;
    _SpinFreq     = 0 ;
    _SpinClock    = 0 ;
    OwnerIsThread = 0 ;
    _previous_owner_tid = 0;
  }
```

几个重要的属性：

- `_owner`指向持有`ObjectMonitor`对象的线程。
- ``

## 参考

1. [《JVM源码分析之synchronized实现》](https://www.jianshu.com/p/c5058b6fe8e5)
