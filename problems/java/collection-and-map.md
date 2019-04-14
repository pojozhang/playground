# Collection和Map

## Collection

### List

有序列表，支持在特定位置插入和查询元素。

```java
// 在index处插入元素。
void add(int index, E element);

// 获取index处的元素。
E get(int index);
```

### Queue

先进先出队列。

```java
// 把元素插入到队列的末尾。
boolean offer(E e);

// 返回队首元素。
E peek();

// 移除并返回队首元素。
E poll();
```

### Deque

双端队列，即可以作为先进先出队列，也可以作为后进先出队列。

```java
// 把元素插入到队列的头部。
boolean offerFirst(E e);

// 把元素插入到队列的尾部。
boolean offerLast(E e);

// 等价于offerLast。
boolean offer(E e);

// 获取队首元素。
E peekFirst();

// 获取末尾元素。
E peekLast();

// 等价于peekFirst。
E peek();

// 移除并返回队首元素。
E pollFirst();

// 移除并返回末尾元素。
E pollLast();

// 等价于pollFirst。
E poll();
```

### Set

元素唯一的集合。

```java
// 增加元素。
boolean add(E e);

// 是否包含元素。
boolean contains(Object o);
```

## Map

键值对。

```java
// 插入键值对。
V put(K key, V value);

// 查找元素。
V get(Object key);
```