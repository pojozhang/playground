# ConcurrentHashMap

![版本](https://img.shields.io/badge/java-10-red.svg)

`ConcurrentHashMap`具有`HashMap`同样的功能，但它是线程安全的，其层次结构如下。

![](resources/concurrent_hashmap_1.png)

在JDK1.8（包括）之后，在结构上`ConcurrentHashMap`和`HashMap`非常类似，也是采用了`数组+链表+红黑数`的形式，但是采用了额外的策略来保证线程安全。

![](resources/concurrent_hashmap_2.png)

下面我们从构造函数看起。

## ConcurrentHashMap()

无参构造器是一个空方法。

```java
public ConcurrentHashMap() {
}
```

## ConcurrentHashMap(int)

该构造方法可以根据传入的初始容量设置字段`sizeCtl`的值。

```java
public ConcurrentHashMap(int initialCapacity) {
    // 检查参数合法性。
    if (initialCapacity < 0)
        throw new IllegalArgumentException();
    int cap = ((initialCapacity >= (MAXIMUM_CAPACITY >>> 1)) ?
                MAXIMUM_CAPACITY :
                // 取大于等于（initialCapacity*1.5+1）的最小的2的幂数。
                // 比如initialCapacity=10，那么cap等于16，因为大于等于（10*1.5+1）的最小的2的幂数是2^4。
                tableSizeFor(initialCapacity + (initialCapacity >>> 1) + 1));
    this.sizeCtl = cap;
}

// 该方法和HashMap中的同名方法是一样的。
// 返回大于等于cap的最小的2的幂数。
// 当 cap = 2 时，方法返回值为2，因为 2 ^ 1 = 2。
// 当 cap = 31 时，方法返回值是32，因为 2 ^ 4 = 16 < 31 而 2 ^ 5 = 32 > 31。
private static final int tableSizeFor(int c) {
    int n = c - 1;
    n |= n >>> 1;
    n |= n >>> 2;
    n |= n >>> 4;
    n |= n >>> 8;
    n |= n >>> 16;
    return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
}
```

字段`sizeCtl`的信息如下所示。

```java
private transient volatile int sizeCtl;
```

它用来控制底层数组的初始化和扩容，不同的取值范围代表不同的情况。

1. -1，表示数组正在被初始化。
2. -N，N是大于1的正整数，表示有N-1个线程正在进行扩容。
3. 大于等于0，当数组未初始化时表示需要初始化的大小，否则表示需要对数组进行扩容的阈值。

## ConcurrentHashMap(int, float)

该构造方法内部调用了另一个构造方法。

```java
public ConcurrentHashMap(int initialCapacity, float loadFactor) {
    this(initialCapacity, loadFactor, 1);
}

public ConcurrentHashMap(int initialCapacity,
                         float loadFactor, int concurrencyLevel) {
    // 检查参数合法性。
    if (!(loadFactor > 0.0f) || initialCapacity < 0 || concurrencyLevel <= 0)
        throw new IllegalArgumentException();
    // 这段代码的目的也是设置sizeCtl字段的值。
    if (initialCapacity < concurrencyLevel)
        initialCapacity = concurrencyLevel;
    long size = (long)(1.0 + (long)initialCapacity / loadFactor);
    int cap = (size >= (long)MAXIMUM_CAPACITY) ?
        MAXIMUM_CAPACITY : tableSizeFor((int)size);
    this.sizeCtl = cap;
}
```

## ConcurrentHashMap(Map<? extends K, ? extends V>)

该构造方法把一个`Map`对象放入当前`ConcurrentHashMap`中。

```java
public ConcurrentHashMap(Map<? extends K, ? extends V> m) {
    this.sizeCtl = DEFAULT_CAPACITY;
    putAll(m);
}

public void putAll(Map<? extends K, ? extends V> m) {
    // 扩容。
    tryPresize(m.size());
    // 遍历源map，通过putVal()方法把键值对放入当前map中。
    for (Map.Entry<? extends K, ? extends V> e : m.entrySet())
        putVal(e.getKey(), e.getValue(), false);
}
```

## put(K, V)

该方法把键值对放入map中，内部调用`putVal()`方法。

```java
public V put(K key, V value) {
    return putVal(key, value, false);
}

final V putVal(K key, V value, boolean onlyIfAbsent) {
    // 键和值都不能是null。
    if (key == null || value == null) throw new NullPointerException();
    // 获得哈希值。
    int hash = spread(key.hashCode());
    int binCount = 0;
    for (Node<K,V>[] tab = table;;) {
        Node<K,V> f; int n, i, fh; K fk; V fv;
        // 如果table数组是空的，那么就进行初始化。
        if (tab == null || (n = tab.length) == 0)
            tab = initTable();
        // 通过Unsafe类获得tab[i]处的节点。
        else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) {
            // 当节点不存在时，用CAS在table[i]处创建新节点，只有当table[i]原本是null时才能创建成功。
            if (casTabAt(tab, i, null, new Node<K,V>(hash, key, value)))
                break;                   // no lock when adding to empty bin
        }
        else if ((fh = f.hash) == MOVED)
            tab = helpTransfer(tab, f);
        else if (onlyIfAbsent // check first node without acquiring lock
                    && fh == hash
                    && ((fk = f.key) == key || (fk != null && key.equals(fk)))
                    && (fv = f.val) != null)
            return fv;
        else {
            V oldVal = null;
            synchronized (f) { // 获取首节点的锁。
                if (tabAt(tab, i) == f) {
                    // fh大于等于0表示首节点是链表节点，也就是说table[i]处是一个链表。
                    if (fh >= 0) {
                        binCount = 1;
                        // 遍历链表。
                        for (Node<K,V> e = f;; ++binCount) {
                            K ek;
                            // 如果要插入的键已经存在并且onlyIfAbsent是false，那么就更新键对应的值，否则中断遍历。
                            if (e.hash == hash &&
                                ((ek = e.key) == key ||
                                    (ek != null && key.equals(ek)))) {
                                oldVal = e.val;
                                if (!onlyIfAbsent)
                                    e.val = value;
                                break;
                            }
                            Node<K,V> pred = e;
                            // 遍历到最后一个节点并创建新的节点。
                            if ((e = e.next) == null) {
                                pred.next = new Node<K,V>(hash, key, value);
                                break;
                            }
                        }
                    }
                    // 首节点是红黑树节点。
                    else if (f instanceof TreeBin) {
                        Node<K,V> p;
                        binCount = 2;
                        // 把键值对插入到红黑树中。
                        if ((p = ((TreeBin<K,V>)f).putTreeVal(hash, key,
                                                        value)) != null) {
                            oldVal = p.val;
                            if (!onlyIfAbsent)
                                p.val = value;
                        }
                    }
                    else if (f instanceof ReservationNode)
                        throw new IllegalStateException("Recursive update");
                }
            }
            if (binCount != 0) {
                // 如果table[i]处节点个数达到或超过了TREEIFY_THRESHOLD，就把链表转成红黑树。
                // TREEIFY_THRESHOLD是值为8的常量。
                if (binCount >= TREEIFY_THRESHOLD)
                    treeifyBin(tab, i);
                if (oldVal != null)
                    return oldVal;
                break;
            }
        }
    }
    // 更新计数。
    addCount(1L, binCount);
    return null;
}

// Hash算法。
static final int spread(int h) {
    return (h ^ (h >>> 16)) & HASH_BITS;
}

// 初始化table数组。
private final Node<K,V>[] initTable() {
    Node<K,V>[] tab; int sc;
    while ((tab = table) == null || tab.length == 0) {
        if ((sc = sizeCtl) < 0)
            // 如果sizeCtl<0，则表示table数组正在被初始化，因此当前线程应该让出CPU时间。
            Thread.yield();
        // 否则使用CAS把sizeCtl设为-1。
        else if (U.compareAndSetInt(this, SIZECTL, sc, -1)) {
            // 如果CAS操作成功，那么操作线程就对table数组进行初始化操作，其它线程就会在下一个循环中执行上面的分支，让出CPU时间。
            try {
                if ((tab = table) == null || tab.length == 0) {
                    // 初始化table数组。
                    int n = (sc > 0) ? sc : DEFAULT_CAPACITY;
                    Node<K,V>[] nt = (Node<K,V>[])new Node<?,?>[n];
                    table = tab = nt;
                    sc = n - (n >>> 2);
                }
            } finally {
                // 设置新的阈值为0.75*table.length。
                sizeCtl = sc;
            }
            break;
        }
    }
    return tab;
}

// 通过Unsafe类获得tab[i]处的节点。
static final <K,V> Node<K,V> tabAt(Node<K,V>[] tab, int i) {
    return (Node<K,V>)U.getObjectAcquire(tab, ((long)i << ASHIFT) + ABASE);
}

// 通过CAS设置tab[i]处的节点。
static final <K,V> boolean casTabAt(Node<K,V>[] tab, int i,
                                    Node<K,V> c, Node<K,V> v) {
    return U.compareAndSetObject(tab, ((long)i << ASHIFT) + ABASE, c, v);
}
```

## addCount(long, int)

增加计数。

```java
private final void addCount(long x, int check) {
    CounterCell[] as; long b, s;
    // baseCount用来记录键值对的数量，它的类型是private transient volatile long baseCount;
    // x表示增量，比如x = 1时表示键值对数量增加1。
    // 尝试用CAS更新baseCount = baseCount + x。
    if ((as = counterCells) != null ||
        !U.compareAndSetLong(this, BASECOUNT, b = baseCount, s = b + x)) {
        // 在高并发情况下只有一个线程可以成功执行CAS操作，其余线程执行失败。
        // 执行失败的线程就把增量写到各自的CounterCell对象中。
        // counterCells维护这些CounterCell对象，它的类型是private transient volatile CounterCell[]。
        CounterCell a; long v; int m;
        boolean uncontended = true;
        // 如果counterCells是空的，或者counterCells[ThreadLocalRandom.getProbe() & m]是null，或者CAS更新CounterCell的value字段失败，那么调用fullAddCount()进一步处理。
        // fullAddCount()方法会初始化counterCells，并通过死循环保证增量写入CounterCell或者baseCount，相关代码较多，这里不再具体展开，可以查看参考文档中的（3）。
        // ThreadLocalRandom.getProbe()产生一个随机数，因此ThreadLocalRandom.getProbe() & m的意思是随机找一个索引。
        // 因为索引是随机的，因此多个线程可能会共享同一个CounterCell对象，所以这里需要通过CAS对CounterCell中的value字段进行更新。
        if (as == null || (m = as.length - 1) < 0 ||
            (a = as[ThreadLocalRandom.getProbe() & m]) == null ||
            !(uncontended =
                U.compareAndSetLong(a, CELLVALUE, v = a.value, v + x))) {
            fullAddCount(x, uncontended);
            return;
        }
        if (check <= 1)
            return;
        // 计算简直对总数，赋值给变量s。
        s = sumCount();
    }

    // 下面这部分代码的作用是判断是否需要对table数组进行扩容，
    // 这就是为什么在之前的put()方法中没有扩容相关的代码。
    if (check >= 0) {
        Node<K,V>[] tab, nt; int n, sc;
        while (s >= (long)(sc = sizeCtl) && (tab = table) != null &&
                (n = tab.length) < MAXIMUM_CAPACITY) {
            int rs = resizeStamp(n);
            if (sc < 0) {
                if ((sc >>> RESIZE_STAMP_SHIFT) != rs || sc == rs + 1 ||
                    sc == rs + MAX_RESIZERS || (nt = nextTable) == null ||
                    transferIndex <= 0)
                    break;
                if (U.compareAndSetInt(this, SIZECTL, sc, sc + 1))
                    transfer(tab, nt);
            }
            else if (U.compareAndSetInt(this, SIZECTL, sc,
                                            (rs << RESIZE_STAMP_SHIFT) + 2))
                transfer(tab, null);
            s = sumCount();
        }
    }
}

// 计算baseCount和所有CounterCell的总和。
final long sumCount() {
    CounterCell[] as = counterCells; CounterCell a;
    long sum = baseCount;
    if (as != null) {
        for (int i = 0; i < as.length; ++i) {
            if ((a = as[i]) != null)
                sum += a.value;
        }
    }
    return sum;
}

// java.util.concurrent.ConcurrentHashMap.CounterCell
@jdk.internal.vm.annotation.Contended static final class CounterCell {
    volatile long value;
    CounterCell(long x) { value = x; }
}
```

## tryPresize(int)

```java
private final void tryPresize(int size) {
    // 计算扩容后的目标大小。
    int c = (size >= (MAXIMUM_CAPACITY >>> 1)) ? MAXIMUM_CAPACITY :
        tableSizeFor(size + (size >>> 1) + 1);
    int sc;
    while ((sc = sizeCtl) >= 0) {
        // table是底层数组。
        // 它的类型是transient volatile Node<K,V>[] table;
        Node<K,V>[] tab = table; int n;
        // table没有被初始化或者是空的，那么进行初始化。
        if (tab == null || (n = tab.length) == 0) {
            n = (sc > c) ? sc : c;
            // 用CAS把sizeCtl赋值为-1，以表示正在对数组进行初始化。
            // 当多个线程并发执行时，只有一个能成功把sizeCtl赋值为-1。
            if (U.compareAndSetInt(this, SIZECTL, sc, -1)) {
                try {
                    // 初始化table。
                    if (table == tab) {
                        Node<K,V>[] nt = (Node<K,V>[])new Node<?,?>[n];
                        table = nt;
                        // n - (n >>> 2) 等价于 n - (n / 4)，也就是 0.75 * n。
                        sc = n - (n >>> 2);
                    }
                } finally {
                    // 下一次扩容的阈值是0.75 * n。
                    sizeCtl = sc;
                }
            }
        }
        else if (c <= sc || n >= MAXIMUM_CAPACITY)
            // 容量足够或已达到最大容量，不进行扩容。
            break;
        else if (tab == table) {
            int rs = resizeStamp(n);
            if (U.compareAndSetInt(this, SIZECTL, sc,
                                    (rs << RESIZE_STAMP_SHIFT) + 2))
                transfer(tab, null);
        }
    }
}
```

## transfer(Node<K,V>[], Node<K,V>[])

```java
private final void transfer(Node<K,V>[] tab, Node<K,V>[] nextTab) {
    int n = tab.length, stride;
    if ((stride = (NCPU > 1) ? (n >>> 3) / NCPU : n) < MIN_TRANSFER_STRIDE)
        stride = MIN_TRANSFER_STRIDE; // subdivide range
    if (nextTab == null) {            // initiating
        try {
            @SuppressWarnings("unchecked")
            Node<K,V>[] nt = (Node<K,V>[])new Node<?,?>[n << 1];
            nextTab = nt;
        } catch (Throwable ex) {      // try to cope with OOME
            sizeCtl = Integer.MAX_VALUE;
            return;
        }
        nextTable = nextTab;
        transferIndex = n;
    }
    int nextn = nextTab.length;
    ForwardingNode<K,V> fwd = new ForwardingNode<K,V>(nextTab);
    boolean advance = true;
    boolean finishing = false; // to ensure sweep before committing nextTab
    for (int i = 0, bound = 0;;) {
        Node<K,V> f; int fh;
        while (advance) {
            int nextIndex, nextBound;
            if (--i >= bound || finishing)
                advance = false;
            else if ((nextIndex = transferIndex) <= 0) {
                i = -1;
                advance = false;
            }
            else if (U.compareAndSetInt
                        (this, TRANSFERINDEX, nextIndex,
                        nextBound = (nextIndex > stride ?
                                    nextIndex - stride : 0))) {
                bound = nextBound;
                i = nextIndex - 1;
                advance = false;
            }
        }
        if (i < 0 || i >= n || i + n >= nextn) {
            int sc;
            if (finishing) {
                nextTable = null;
                table = nextTab;
                sizeCtl = (n << 1) - (n >>> 1);
                return;
            }
            if (U.compareAndSetInt(this, SIZECTL, sc = sizeCtl, sc - 1)) {
                if ((sc - 2) != resizeStamp(n) << RESIZE_STAMP_SHIFT)
                    return;
                finishing = advance = true;
                i = n; // recheck before commit
            }
        }
        else if ((f = tabAt(tab, i)) == null)
            advance = casTabAt(tab, i, null, fwd);
        else if ((fh = f.hash) == MOVED)
            advance = true; // already processed
        else {
            synchronized (f) {
                if (tabAt(tab, i) == f) {
                    Node<K,V> ln, hn;
                    if (fh >= 0) {
                        int runBit = fh & n;
                        Node<K,V> lastRun = f;
                        for (Node<K,V> p = f.next; p != null; p = p.next) {
                            int b = p.hash & n;
                            if (b != runBit) {
                                runBit = b;
                                lastRun = p;
                            }
                        }
                        if (runBit == 0) {
                            ln = lastRun;
                            hn = null;
                        }
                        else {
                            hn = lastRun;
                            ln = null;
                        }
                        for (Node<K,V> p = f; p != lastRun; p = p.next) {
                            int ph = p.hash; K pk = p.key; V pv = p.val;
                            if ((ph & n) == 0)
                                ln = new Node<K,V>(ph, pk, pv, ln);
                            else
                                hn = new Node<K,V>(ph, pk, pv, hn);
                        }
                        setTabAt(nextTab, i, ln);
                        setTabAt(nextTab, i + n, hn);
                        setTabAt(tab, i, fwd);
                        advance = true;
                    }
                    else if (f instanceof TreeBin) {
                        TreeBin<K,V> t = (TreeBin<K,V>)f;
                        TreeNode<K,V> lo = null, loTail = null;
                        TreeNode<K,V> hi = null, hiTail = null;
                        int lc = 0, hc = 0;
                        for (Node<K,V> e = t.first; e != null; e = e.next) {
                            int h = e.hash;
                            TreeNode<K,V> p = new TreeNode<K,V>
                                (h, e.key, e.val, null, null);
                            if ((h & n) == 0) {
                                if ((p.prev = loTail) == null)
                                    lo = p;
                                else
                                    loTail.next = p;
                                loTail = p;
                                ++lc;
                            }
                            else {
                                if ((p.prev = hiTail) == null)
                                    hi = p;
                                else
                                    hiTail.next = p;
                                hiTail = p;
                                ++hc;
                            }
                        }
                        ln = (lc <= UNTREEIFY_THRESHOLD) ? untreeify(lo) :
                            (hc != 0) ? new TreeBin<K,V>(lo) : t;
                        hn = (hc <= UNTREEIFY_THRESHOLD) ? untreeify(hi) :
                            (lc != 0) ? new TreeBin<K,V>(hi) : t;
                        setTabAt(nextTab, i, ln);
                        setTabAt(nextTab, i + n, hn);
                        setTabAt(tab, i, fwd);
                        advance = true;
                    }
                }
            }
        }
    }
}
```

## get(Object)

该方法通过键查找对应的值。

```java
public V get(Object key) {
    Node<K,V>[] tab; Node<K,V> e, p; int n, eh; K ek;
    // 计算键对应的哈希值。
    int h = spread(key.hashCode());
    // 如果table是空的，或者table[(table.length - 1) & h)]处没有节点，那么方法返回null。
    if ((tab = table) != null && (n = tab.length) > 0 &&
        (e = tabAt(tab, (n - 1) & h)) != null) {
        // 对比首节点的键是否匹配目标键，如果匹配那么直接返回首节点的值。
        if ((eh = e.hash) == h) {
            if ((ek = e.key) == key || (ek != null && key.equals(ek)))
                return e.val;
        }
        // 首节点哈希值小于0表示该节点是一个红黑树节点或者正在扩容。
        else if (eh < 0)
            return (p = e.find(h, key)) != null ? p.val : null;
        // 其它情况下遍历链表，寻找匹配的键。
        while ((e = e.next) != null) {
            if (e.hash == h &&
                ((ek = e.key) == key || (ek != null && key.equals(ek))))
                return e.val;
        }
    }
    // 找不到匹配的键，返回null。
    return null;
}
```

## remove(Object)

删除键值对，内部调用`replaceNode()`方法。

```java
public V remove(Object key) {
    return replaceNode(key, null, null);
}

final V replaceNode(Object key, V value, Object cv) {
    int hash = spread(key.hashCode());
    for (Node<K,V>[] tab = table;;) {
        Node<K,V> f; int n, i, fh;
        if (tab == null || (n = tab.length) == 0 ||
            (f = tabAt(tab, i = (n - 1) & hash)) == null)
            break;
        else if ((fh = f.hash) == MOVED)
            tab = helpTransfer(tab, f);
        else {
            V oldVal = null;
            boolean validated = false;
            synchronized (f) {
                if (tabAt(tab, i) == f) {
                    if (fh >= 0) {
                        validated = true;
                        for (Node<K,V> e = f, pred = null;;) {
                            K ek;
                            if (e.hash == hash &&
                                ((ek = e.key) == key ||
                                    (ek != null && key.equals(ek)))) {
                                V ev = e.val;
                                if (cv == null || cv == ev ||
                                    (ev != null && cv.equals(ev))) {
                                    oldVal = ev;
                                    if (value != null)
                                        e.val = value;
                                    else if (pred != null)
                                        pred.next = e.next;
                                    else
                                        setTabAt(tab, i, e.next);
                                }
                                break;
                            }
                            pred = e;
                            if ((e = e.next) == null)
                                break;
                        }
                    }
                    else if (f instanceof TreeBin) {
                        validated = true;
                        TreeBin<K,V> t = (TreeBin<K,V>)f;
                        TreeNode<K,V> r, p;
                        if ((r = t.root) != null &&
                            (p = r.findTreeNode(hash, key, null)) != null) {
                            V pv = p.val;
                            if (cv == null || cv == pv ||
                                (pv != null && cv.equals(pv))) {
                                oldVal = pv;
                                if (value != null)
                                    p.val = value;
                                else if (t.removeTreeNode(p))
                                    setTabAt(tab, i, untreeify(t.first));
                            }
                        }
                    }
                    else if (f instanceof ReservationNode)
                        throw new IllegalStateException("Recursive update");
                }
            }
            if (validated) {
                if (oldVal != null) {
                    if (value == null)
                        addCount(-1L, -1);
                    return oldVal;
                }
                break;
            }
        }
    }
    return null;
}
```

## size()

返回键值对的数量，内部调用`sumCount()`方法。

```java
public int size() {
    long n = sumCount();
    return ((n < 0L) ? 0 :
            (n > (long)Integer.MAX_VALUE) ? Integer.MAX_VALUE :
            (int)n);
}
```

## Nullable

对于`ConcurrentHashMap`中的项，键和值**都不能**是`null`。

```java
public static void main(String[] args) {
    Map<String, String> map = new ConcurrentHashMap<>();
    // 以下3中用法都是不允许的，会抛出java.lang.NullPointerException异常。
    map.put(null, "hello");
    map.put("hello", null);
    System.out.println(map.get(null));
}
```

## 参考

1. [《Java7/8 中的 HashMap 和 ConcurrentHashMap 全解析》](https://javadoop.com/post/hashmap#Java8%20ConcurrentHashMap)
2. [《深入浅出ConcurrentHashMap1.8》](https://www.jianshu.com/p/c0642afe03e0)
3. [《谈谈ConcurrentHashMap1.7和1.8的不同实现》](https://www.jianshu.com/p/e694f1e868ec)
4. [《为并发而生的 ConcurrentHashMap（Java 8）》](https://cloud.tencent.com/developer/article/1013643)
