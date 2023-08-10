# ThreadLocal

`ThreadLocal`的意思是线程局部变量。同一个`ThreadLocal`包含的对象，在不同的线程中有不同的副本，线程只能访问自己的副本，而不能访问其它线程的副本。

## 原理

`Thread`对象有一个`threadLocals`字段，它的类型是`ThreadLocalMap`。

```java
// java.lang.Thread#threadLocals
ThreadLocal.ThreadLocalMap threadLocals = null;
```

`ThreadLocalMap`是一个map，键是`ThreadLocal`类型，值是`Object`类型。

当调用`ThreadLocal`的`set()`方法时会获取`Thread`对象的`threadLocals`字段，如果是`null`那么就进行初始化，并且把当前`ThreadLocal`对象作为键，`value`作为值放到map中。

```java
// java.lang.ThreadLocal#set
public void set(T value) {
    Thread t = Thread.currentThread();
    ThreadLocalMap map = getMap(t);
    if (map != null)
        map.set(this, value);
    else
        createMap(t, value);
}

ThreadLocalMap getMap(Thread t) {
    return t.threadLocals;
}

void createMap(Thread t, T firstValue) {
    t.threadLocals = new ThreadLocalMap(this, firstValue);
}
```

类似的，当调用`ThreadLocal`的`get()`方法时，就会获取当前`Thread`对象的`threadLocals`字段，如果是`null`那么就进行初始化并设置初始值，否则就把当前`ThreadLocal`对象作为键查询对应的值。

```java
// java.lang.ThreadLocal#get
public T get() {
    Thread t = Thread.currentThread();
    ThreadLocalMap map = getMap(t);
    if (map != null) {
        ThreadLocalMap.Entry e = map.getEntry(this);
        if (e != null) {
            @SuppressWarnings("unchecked")
            T result = (T)e.value;
            return result;
        }
    }
    return setInitialValue();
}

private T setInitialValue() {
    T value = initialValue();
    Thread t = Thread.currentThread();
    ThreadLocalMap map = getMap(t);
    if (map != null)
        map.set(this, value);
    else
        createMap(t, value);
    return value;
}
```

### 弱引用

`ThreadLocalMap`中的`Entry`对象用到了弱引用，需要注意的是这里仅对Map中的key也就是`TheadLocal`对象使用了弱引用，对Map中的值还是强引用。当外部对象对`ThreadLocal`的强引用结束后，`ThreadLocal`对象上就只剩下了弱引用，在下一次GC时，`Entry`引用的`ThreadLocal`对象就会被回收。如果是强引用，而开发者又没有手动调用`remove()`方法进行清理，那么该`ThreadLocal`对象就一直保留在`ThreadLocalMap`中，那么就可能会发生内存泄漏。

```java
// java.lang.ThreadLocal.ThreadLocalMap.Entry
static class Entry extends WeakReference<ThreadLocal<?>> {

    Object value;

    Entry(ThreadLocal<?> k, Object v) {
        super(k);
        value = v;
    }
}
```

比如，下面的代码在`method()`方法中创建了一个`ThreadLocal`类型的局部变量`threadLocal`，当方法执行完回到`main()`方法后对该对象的强引用就不复存在，如果`Entry`类没有使用弱引用，那么它将持有对`ThreadLocal`的强引用导致其无法被回收。

```java
public static void main(String[] args) {
    method()
}

static void method() {
    ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
    threadLocal.set(1);
}
```

### 是否会内存泄漏

如果没有手动调用`remove()`方法清理`TheadLocal`对象，那么可能造成内存泄漏。

`ThreadLocal`提供了自动清理`Entry`的能力，但是存在滞后性。首先自动清理也需要通过调用一些方法进行触发，因此当不再操作`ThreadLocal`对象时，就无法触发自动清理。其次，即使触发了自动清理，并不能保证将所有过期的数据全部清理完。

`ThreadLocal`在get()、set()、remove()方法被调用时都会去扫描key为null的Entry，并把对应的value也设置为null，相关清理的方法如下。注意以下代码块中`for`循环的结束条件，该方法只是清理连续的key是`null`的Entry，而不是清理所有的无效Entry。

```java
private int expungeStaleEntry(int staleSlot) {
    Entry[] tab = table;
    int len = tab.length;

    tab[staleSlot].value = null;
    tab[staleSlot] = null;
    size--;

    Entry e;
    int i;
    for (i = nextIndex(staleSlot, len);
            (e = tab[i]) != null; // 当Entry是null时不再继续清理。
            i = nextIndex(i, len)) {
        ThreadLocal<?> k = e.get();
        // 如果key是null，把value也设置成null。
        if (k == null) {
            e.value = null;
            tab[i] = null;
            size--;
        } else {
            int h = k.threadLocalHashCode & (len - 1);
            if (h != i) {
                tab[i] = null;

                while (tab[h] != null)
                    h = nextIndex(h, len);
                tab[h] = e;
            }
        }
    }
    return i;
}
```

所以一个好的习惯是使用完ThreadLocal对象后主动调用remove()方法进行清理。
