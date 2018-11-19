# CAS

CAS，全称CompareAndSwap（或CompareAndSet），即比较并交换，是并发编程中经常用到的一种技术。

我们经常在多线程环境下对一个字段进行自增操作，比如以下代码。

```java
public class Test {

    private int number;

    public void inc() {
        number++;
    }
}
```

但是由于自增操作不是一个原子操作，它分为`读取-修改-写回`三个步骤，在多线程环境下就有可能导致这三个步骤互相交错的执行，从而导致结果不正确。比如有两个线程同时执行自增操作，我们期望的执行过程和结果是这样的。

![](resources/cas_1.png)

但实际上它有可能是这样的。

![](resources/cas_2.png)

由于线程1和线程2的代码交错执行，导致最后的结果不正确。

通常我们可以用`synchronized`和简单的解决这个问题。

```java
public class Test {

    private int number;

    public synchronized void inc() {
        number++;
    }
}
```

但是`synchronized`关键字需要进行加锁操作，效率不高，因此JDK1.5引入了CAS，它不需要加锁，效率更高并且易于使用。

```java
public class Test {

    private AtomicInteger number = new AtomicInteger();

    public void inc() {
        number.incrementAndGet();
    }
}
```

现在我们来看看它是如何实现的。

```java
// java.util.concurrent.atomic.AtomicInteger#incrementAndGet
private static final long VALUE;

public final int incrementAndGet() {
    return U.getAndAddInt(this, VALUE, 1) + 1;
}
```

代码中的`U`是一个`Unsafe`类的实例，之所以叫`Unsafe`是因为它提供了一些比较底层的操作，比如可以直接读写内存地址。

```java
private static final jdk.internal.misc.Unsafe U = jdk.internal.misc.Unsafe.getUnsafe();
```

代码中的`VALUE`是一个`long`型的静态常量，它的值是在静态块中被初始化的。

```java
private static final long VALUE;

static {
    try {
        VALUE = U.objectFieldOffset
            (AtomicInteger.class.getDeclaredField("value"));
    } catch (ReflectiveOperationException e) {
        throw new Error(e);
    }
}
```

方法`jdk.internal.misc.Unsafe#objectFieldOffset`用来获取一个字段相对于对象的起始内存地址的字节偏移量。这里的偏移量是一个相对值，因此对于同一个类型下的不同对象，同一个字段的偏移量是一样的。

下面我们进入`jdk.internal.misc.Unsafe#getAndAddInt`方法。

```java
// jdk.internal.misc.Unsafe#getAndAddInt
public final int getAndAddInt(Object o, long offset, int delta) {
    int v;
    do {
        // jdk.internal.misc.Unsafe#getIntVolatile是一个本地方法，直接从主存中获取对象指定偏移量处的int值。
        // 本例中获取到的是Test对象中number字段的最新值。
        v = getIntVolatile(o, offset);
    } while (!weakCompareAndSetInt(o, offset, v, v + delta));
    return v;
}

public final boolean weakCompareAndSetInt(Object o, long offset,
                                          int expected,
                                          int x) {
    // 调用本地方法jdk.internal.misc.Unsafe#compareAndSetInt。
    return compareAndSetInt(o, offset, expected, x);
}
```

方法`jdk.internal.misc.Unsafe#weakCompareAndSetInt`又调用了一个本地方法`jdk.internal.misc.Unsafe#compareAndSetInt`。

```cpp
// jdk.internal.misc.Unsafe#compareAndSetInt
UNSAFE_ENTRY(jboolean, Unsafe_CompareAndSetInt(JNIEnv *env, jobject unsafe, jobject obj, jlong offset, jint e, jint x)) {
  oop p = JNIHandles::resolve(obj);
  jint* addr = (jint *)index_oop_from_field_offset_long(p, offset);

  return (jint)(Atomic::cmpxchg(x, addr, e)) == e;
} UNSAFE_END
```

主要是通过`Atomic::cmpxchg`这个方法来进行比较和交换，然而这个方法在不同硬件平台下有不同的实现。

我们来看下Linux在x86架构上的实现，你可以在[这里](https://github.com/unofficial-openjdk/openjdk/blob/jdk9/jdk9/hotspot/src/os_cpu/linux_x86/vm/atomic_linux_x86.hpp)找到源码。

```cpp
inline jint Atomic::cmpxchg (jint exchange_value, volatile jint* dest, jint compare_value, cmpxchg_memory_order order) {
  // 判断是不是多处理器环境。
  int mp = os::is_MP();
  // __asm__表示在C++中嵌入汇编语言。
  __asm__ volatile (LOCK_IF_MP(%4) "cmpxchgl %1,(%3)"
                    : "=a" (exchange_value)
                    : "r" (exchange_value), "a" (compare_value), "r" (dest), "r" (mp)
                    : "cc", "memory");
  return exchange_value;
}

#define LOCK_IF_MP(mp) "cmp $0, " #mp "; je 1f; lock; 1: "
```

C++中嵌入汇编语言的格式如下。

```x86asm
asm ( assembler template
      : output operands                  /* optional */
      : input operands                   /* optional */
      : list of clobbered registers      /* optional */
    );
```

- template
  模板。本例中的模板是`cmpxchgl %1,(%3)`。`%1`是指第2个操作数，`%3`是指第4个输入操作数。操作数包括输出操作数和输入操作数，编号从`0`开始，按照先输出操作数，后输入操作数的顺序进行编号。
- output operands
  输出操作数。这里是`=a`，对应`eax`寄存器。
- input operands
  输入操作数。按照顺序，第1个是`exchange_value`，第2个是`compare_value`，第3个是`dest`，第4个是`mp`。`r`代表任意寄存器，`a`代表`eax`寄存器。
- list of clobbered registers
  额外的参数。`cc`参数表示编译器`cmpxchgl`的执行将影响到标志寄存器；`memory`参数是让编译器重新从内存中读取变量的值，实现了`volatile`。

`cmpxchgl %1,(%3)`其实就是`cmpxchgl exchange_value,dest`，它的原型是`CMPXCHG <dest>,<src>`。`cmpxchgl`会比较`eax`寄存器中的值和`dest`的值，如果相等那么就把`exchange_value`赋值给`dest`，否则把`dest`的值赋值给`eax`寄存器。通过`"a" (compare_value)`可以知道，`eax`寄存器里的值就是`compare_value`，因此这里就会判断`compare_value`和`dest`的值是否相等，这其实就是汇编层面的CAS操作。

还有一些其它平台的实现，比如Windows、Solaris在x86上的实现、Linux在ARM上的实现等，这里不再具体展开。