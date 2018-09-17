# ArrayList

`ArrayList`是最常用的集合类之一，它实现了`RandomAccess`、`Cloneable`、`Serializable`接口，说明它具备随机访问、可复制、可序列化的功能，通过IDE我们可以看到该类的层次结构如下图所示：

![](resources/arraylist_1.png)

我们知道普通数组缺点的是其大小是固定的，如果数组内的元素变少了或增多了数组的大小并不会随之缩小或扩大，使用的时候不够灵活，`ArrayList`通过在内部动态的调整数组的大小解决了这个问题。下面我们从构造方法看起。

## 构造方法

### ArrayList()

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

### ArrayList(int)

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

### ArrayList(Collection)

```java
public ArrayList(Collection<? extends E> c) {
    elementData = c.toArray();
    if ((size = elementData.length) != 0) {
        if (elementData.getClass() != Object[].class)
            elementData = Arrays.copyOf(elementData, size, Object[].class);
    } else {
        this.elementData = EMPTY_ELEMENTDATA;
    }
}
```