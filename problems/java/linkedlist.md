# LinkedList

`LinkedList`是一个底层使用双向循环链表实现的列表，它实现了`List`和`Deque`接口说明它既可以作为一个列表使用，又可以作为双端队列使用。`List`和`Deque`的区别是前者可以通过索引定位到元素，后者可以同时在集合首尾增加或删除元素，从而实现先进先出的队列或是后进先出的栈。它没有实现`RandomAccess`接口，这表示它不支持随机访问，只支持顺序访问，这是它和`ArrayList`最大的区别。

![](resources/linkedlist_1.png)

下面我们从构造方法看起，源码出自JDK10。

## LinkedList()

这其实是一个空方法，没有做任何事情。

```java
public LinkedList() {
}
```

## add(E)

添加一个元素到列表尾部。

```java
public boolean add(E e) {
    linkLast(e);
    return true;
}

void linkLast(E e) {
    final Node<E> l = last;
    // 创建一个新的节点，节点的prev指向最后一个节点。
    final Node<E> newNode = new Node<>(l, e, null);
    // 修改last指向刚才创建的节点。
    last = newNode;
    if (l == null)
        // 如果l为null，说明列表是空的，把first指向刚才创建的节点。
        first = newNode;
    else
        // 如果列表不是空的，那么就把原来的最后一个节点的next指向新创建的节点。
        l.next = newNode;
    // size是私有字段，记录实际的元素个数。
    size++;
    modCount++;
}

// Node是LinkedList中的内部类，一个Node就是双向循环链表中的一个节点。
private static class Node<E> {
    E item;
    Node<E> next;
    Node<E> prev;

    Node(Node<E> prev, E element, Node<E> next) {
        this.item = element;
        this.next = next;
        this.prev = prev;
    }
}
```

一个双向循环链表的结构如图所示。

![](resources/linkedlist_2.png)

## add(int, E)

```java
public void add(int index, E element) {
    checkPositionIndex(index);

    if (index == size)
        linkLast(element);
    else
        linkBefore(element, node(index));
}

private void checkPositionIndex(int index) {
    if (!isPositionIndex(index))
        throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
}

void linkBefore(E e, Node<E> succ) {
    final Node<E> pred = succ.prev;
    final Node<E> newNode = new Node<>(pred, e, succ);
    succ.prev = newNode;
    if (pred == null)
        first = newNode;
    else
        pred.next = newNode;
    size++;
    modCount++;
}
```