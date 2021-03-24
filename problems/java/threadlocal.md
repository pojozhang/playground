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

`ThreadLocalMap`中的`Entry`对象用到了弱引用。当外部对象对`ThreadLocal`的强引用结束后，在下一次GC时，`Entry`引用的`ThreadLocal`对象就会被回收。如果不是弱引用，而开发者又没有手动调用`remove()`方法进行清理，那么很容易发生内存泄漏。

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

### 内存泄漏

虽然ThreadLocalMap.Entry的key是弱引用，但是value确实强引用，它只会在Thread对象结束生命周期时才会被回收，如果Thread生命周期很长（比如线程池中的线程），那么就有可能引起内存泄漏。

ThreadLocal在get()、set()、remove()方法被调用时都会去扫描key为null的Entry，并把对应的value也设置为null，相关清理的方法如下。

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
            (e = tab[i]) != null;
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
