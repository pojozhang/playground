# ArrayList

`ArrayList`是最常用的集合类之一，它实现了`RandomAccess`、`Cloneable`、`Serializable`接口，说明它具备随机访问、可复制、可序列化的功能，通过IDE我们可以看到该类的层次结构如下图所示：

![](resources/arraylist_1.png)

我们知道普通数组缺点的是其大小是固定的，如果数组内的元素变少了或增多了数组的大小并不会随之缩小或扩大，使用的时候不够灵活，`ArrayList`通过在内部动态的调整数组的大小解决了这个问题。

下面我们从构造方法看起，源码出自JDK10。

## ArrayList()

```java
public ArrayList() {
    this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
}
```

这是我们最常用的构造方法，这里的`elementData`就是`ArrayList`用来存储元素的数组，`DEFAULTCAPACITY_EMPTY_ELEMENTDATA`是一个大小为0的空数组，因此该构造方法就是把`elementData`初始化为一个空数组。

```java
private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
transient Object[] elementData;
```

## ArrayList(int)

第二个构造方法和第一个类似，只是可以为数组指定一个初始大小。

```java
public ArrayList(int initialCapacity) {
    if (initialCapacity > 0) {
        this.elementData = new Object[initialCapacity];
    } else if (initialCapacity == 0) {
        this.elementData = EMPTY_ELEMENTDATA;
    } else {
        throw new IllegalArgumentException("Illegal Capacity: "+
                                               initialCapacity);
    }
}
```

## ArrayList(Collection)

第三个构造方法把`Collection`转成数组赋值给`elementData`，如果`Collection`是空集合那么就把`elementData`初始化为空数组。

```java
public ArrayList(Collection<? extends E> c) {
    elementData = c.toArray();
    if ((size = elementData.length) != 0) {
        // 如果类型不是Object[].class就用Arrays.copyOf()转成Object[].class
        // 这其实是为了修复一个久远的bug，https://bugs.openjdk.java.net/browse/JDK-6260652
        // 在Collection接口的文档中写着toArray(new Object[0])和toArray()两个方法是完全一致的，但是在某些情况下，在参数相同的情况下两者返回值的类型却不一样，toArray(new Object[0])总是返回Object[]类型，而toArray()有时候会返回实际类型，如String[]。这段代码修复了这个问题，但由于时间久远，在JDK10中我没能复现这个问题。
        if (elementData.getClass() != Object[].class)
            elementData = Arrays.copyOf(elementData, size, Object[].class);
    } else {
        this.elementData = EMPTY_ELEMENTDATA;
    }
}
```

## add(E)

```java
public boolean add(E e) {
    modCount++;
    add(e, elementData, size);
    return true;
}
```

该方法用于把元素放到列表的末位。其中，`size`是`int`类型的字段，表示当前列表实际的元素个数而 **不是** `elementData`的容量。其内部调用以下方法：

```java
private void add(E e, Object[] elementData, int s) {
    // 当元素个数等于底层数组的长度时调用grow()方法进行扩容。
    if (s == elementData.length)
        elementData = grow();
    elementData[s] = e;
    // 更新实际元素个数。
    size = s + 1;
}

private Object[] grow() {
    return grow(size + 1);
}

private Object[] grow(int minCapacity) {
    // 把原数组复制到扩容后的数组。
    // 底层使用System.arraycopy()进行复制。
    return elementData = Arrays.copyOf(elementData, newCapacity(minCapacity));
}

private int newCapacity(int minCapacity) {
    // 记录当前容量。
    int oldCapacity = elementData.length;
    // 新的容量 = 当前容量的1.5倍。
    int newCapacity = oldCapacity + (oldCapacity >> 1);
    // 如果新的容量不大于参数所要求的最小容量，
    // 分3种情况：
    // 1. newCapacity没有溢出，那么0<=newCapacity<=minCapacity。
    // 2. newCapacity溢出，minCapacity没有溢出，那么newCapacity<0<=minCapacity。
    // 3. newCapacity溢出，minCapacity溢出，那么newCapacity<=minCapacity<0
    if (newCapacity - minCapacity <= 0) {
        // 如果elementData是空数组，那么就返回DEFAULT_CAPACITY和minCapacity较大的数，
        // DEFAULT_CAPACITY是值为10的常量，因此当数组为空时，首次扩容后的大小至少是10.
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA)
            return Math.max(DEFAULT_CAPACITY, minCapacity);
        // 如果minCapacity小于0，说明minCapacity值太大导致溢出。
        if (minCapacity < 0)
            throw new OutOfMemoryError();
        return minCapacity;
    }
    // 如果新的容量大于参数所要求的最小容量，
    // 分几种情况：
    // 1. newCapacity没有溢出，minCapacity没有溢出，那么0<=minCapacity<newCapacity。
    // 2. newCapacity没有溢出，minCapacity溢出，那么minCapacity<0<=newCapacity。
    // 3. newCapacity溢出，minCapacity溢出，那么minCapacity<newCapacity<0
    // 对于第3种情况，因为newCapacity溢出，那么很有可能满足newCapacity - MAX_ARRAY_SIZE <= 0，这个时候return出去的newCapacity就会是负数，但是实际上是不会发生的，因为MAX_ARRAY_SIZE=Integer.MAX_VALUE - 8，因此newCapacity<=-10的时候，newCapacity - MAX_ARRAY_SIZE一定大于0，而newCapacity在溢出时的最大值就是oldCapacity等于Integer.MAX_VALUE的时候，也就是Integer.MAX_VALUE + (Integer.MAX_VALUE >> 1) = -1073741826，远远小于-10，因此可以保证newCapacity溢出时，返回值一定是走hugeCapacity(minCapacity)这个分支。
    return (newCapacity - MAX_ARRAY_SIZE <= 0)
        ? newCapacity
        : hugeCapacity(minCapacity);
}

private static int hugeCapacity(int minCapacity) {
    // 如果minCapacity<0，那么就表示溢出了。
    if (minCapacity < 0)
        throw new OutOfMemoryError();
    return (minCapacity > MAX_ARRAY_SIZE)
        ? Integer.MAX_VALUE
        : MAX_ARRAY_SIZE;
}
```

## ConcurrentModificationException

在上面的`add(E)`方法中我们看到有一步操作`modCount++;`，看上去像是一个计数器，那么它是用来做什么的呢？

```java
protected transient int modCount = 0;
```

`modCount`是一个int类型的字段，主要用途是当我们用`iterator`进行遍历时检测列表是否在遍历途中增加或删除过元素。

```java
List<Integer> list = new ArrayList<>();
list.add(1);
list.add(2);
Iterator<Integer> iterator = list.iterator();
list.remove(0);
// ConcurrentModificationException异常，因为在遍历时从列表中移除了一个元素。
iterator.next();
```

每次添加或删除元素就会修改`modCount`的值，当我们调用`iterator()`创建一个迭代器时，它会把`modCount`复制一份，并在每次迭代时与modCount`的当前值进行比对，如果对不上那就说明列表已经被修改过了，此时抛出`ConcurrentModificationException`异常。该设计的初衷是为了防止一个线程在遍历列表时另一个线程增加或删除了元素。

我们通常用以下代码形式对列表进行遍历，先判断`hasNext()`，再通过`next()`获得元素，但是这两哥操作不是原子的，在多线程情况下当我们执行`hasNext()`之后，列表中的元素数量可能已经发生变化，因此可能出错，比如一个线程删除了一些元素，那么执行`iterator.next()`就有可能越界。

```java
while (iterator.hasNext()) {
    iterator.next();
}
```

```java
public boolean hasNext() {
    // cursor是下一次要迭代的数组的索引。
    return cursor != size;
}
```

## add(int, E)

把元素插入到指定位置。

```java
public void add(int index, E element) {
    // 检查index是否合法。
    rangeCheckForAdd(index);
    modCount++;
    final int s;
    Object[] elementData;
    // 如果元素个数等于数组大小，那么说明数组容量已用完，需要扩容。
    if ((s = size) == (elementData = this.elementData).length)
        elementData = grow();
    // 把index以及之后的元素往后移一位。
    System.arraycopy(elementData, index,
                     elementData, index + 1,
                     s - index);
    // index处放置插入的元素。
    elementData[index] = element;
    size = s + 1;
}

private void rangeCheckForAdd(int index) {
    // 这里允许index等于size，但不能大于size。
    if (index > size || index < 0)
        throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
}
```

以下代码可以正确执行：

```java
// 初始化一个有2个元素的列表。
List<Integer> list = new ArrayList<>();
list.add(1);
list.add(2);

// 在索引是2的地方插入元素3。
list.add(2, 3);
```

以下代码不能正确执行：

```java
// 初始化一个有2个元素的列表。
List<Integer> list = new ArrayList<>();
list.add(1);
list.add(2);

// 在索引是3的地方插入元素3。
// 抛出IndexOutOfBoundsException异常，因为索引3大于当前元素个数2。
list.add(3, 3);
```

## addAll(Collection<? extends E>) {

把一个集合中的元素插入到列表中。

```java
public boolean addAll(Collection<? extends E> c) {
    Object[] a = c.toArray();
    modCount++;
    int numNew = a.length;
    if (numNew == 0)
        return false;
    Object[] elementData;
    final int s;
    // 如果集合中元素的个数超过了elementData数组剩余的容量，那么需要进行扩容。
    if (numNew > (elementData = this.elementData).length - (s = size))
        elementData = grow(s + numNew);
    System.arraycopy(a, 0, elementData, s, numNew);
    size = s + numNew;
    return true;
}
```

## remove(int)

删除索引处的元素。

```java
public E remove(int index) {
    // 越界检查
    Objects.checkIndex(index, size);
    final Object[] es = elementData;
    E oldValue = (E) es[index];
    fastRemove(es, index);
    return oldValue;
}

private void fastRemove(Object[] es, int i) {
    modCount++;
    final int newSize;
    // 如果要删除的元素不是最后一个元素，那么就把i之后的元素全部向前移动一位。
    if ((newSize = size - 1) > i)
        System.arraycopy(es, i + 1, es, i, newSize - i);
    // 把最后一个元素置为null，并更新size。
    es[size = newSize] = null;
}
```

## remove(Object)

删除一个元素。

```java
public boolean remove(Object o) {
    final Object[] es = elementData;
    final int size = this.size;
    int i = 0;
    // 先便利数组进行查找。
    found: {
        if (o == null) {
            for (; i < size; i++)
                if (es[i] == null)
                    break found;
        } else {
            for (; i < size; i++)
                if (o.equals(es[i]))
                    break found;
        }
        return false;
    }
    // 再使用索引进行删除。
    fastRemove(es, i);
    return true;
}
```

## get(int)

获取索引处的元素。

```java
public E get(int index) {
    // 检查索引是否越界。
    Objects.checkIndex(index, size);
    // 直接返回数组中对应索引处的元素。
    return elementData(index);
}

E elementData(int index) {
    return (E) elementData[index];
}
```

## indexOf(Object)

查找目标对象。

```java
public int indexOf(Object o) {
    // 如果目标对象是null，那么遍历列表，返回第一个元素是null的索引号。
    if (o == null) {
        for (int i = 0; i < size; i++)
            if (elementData[i]==null)
                return i;
    } else {
        // 如果目标对象不是null，那么对于列表中的每个元素用equals方法进行比较，返回第一个等于目标对象的索引号。
        for (int i = 0; i < size; i++)
            if (o.equals(elementData[i]))
                return i;
    }
    // 找不到则返回-1。
    return -1;
}
```

`lastIndexOf(Object)`方法除了遍历时是从列表末位开始从后向前遍历外其余逻辑和`lastIndexOf(Object)`一致。

## trimToSize()

