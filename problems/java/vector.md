# Vector

`Vector`和`ArrayList`类似，底层也是一个数组，当数组存满时会自动扩容，它和`ArrayList`的主要区别是：

1. 扩容后大小默认是原来的2倍，而`ArrayList`扩大为原来的1.5倍。

```java
private int newCapacity(int minCapacity) {
    int oldCapacity = elementData.length;
    // 扩容后大小默认是原来的2倍。
    int newCapacity = oldCapacity + ((capacityIncrement > 0) ?
                                    capacityIncrement : oldCapacity);
    if (newCapacity - minCapacity <= 0) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return minCapacity;
    }
    return (newCapacity - MAX_ARRAY_SIZE <= 0)
        ? newCapacity
        : hugeCapacity(minCapacity);
}
```

2. `Vector`是线程安全的，通过在方法前使用`synchronized`关键字实现线程安全。

```java
public synchronized boolean add(E e) {
    modCount++;
    add(e, elementData, elementCount);
    return true;
}
```