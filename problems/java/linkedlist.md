# LinkedList

![版本](https://img.shields.io/badge/java-10-blue.svg)

`LinkedList`是一个底层使用双向链表实现的列表，它实现了`List`和`Deque`接口说明它既可以作为一个列表使用，又可以作为双端队列使用。`List`和`Deque`的区别是前者可以通过索引定位到元素，后者可以同时在集合首尾增加或删除元素，从而实现先进先出的队列或是后进先出的栈。它没有实现`RandomAccess`接口，这表示它不支持随机访问，只支持顺序访问，这是它和`ArrayList`最大的区别。

![](resources/linkedlist_1.png)

下面我们从构造方法看起。

## LinkedList()

这其实是一个空方法，没有做任何事情。

```java
public LinkedList() {
}
```

## LinkedList(Collection<? extends E>)

通过`Collection`生成一个链表。

```java
public LinkedList(Collection<? extends E> c) {
    this();
    addAll(c);
}

public boolean addAll(Collection<? extends E> c) {
    // size是类成员，记录实际的元素个数，初始值为0。
    // transient int size;
    return addAll(size, c);
}

// 在指定位置插入集合。
public boolean addAll(int index, Collection<? extends E> c) {
    // 越界检查。
    checkPositionIndex(index);
    // 集合c转为数组。
    Object[] a = c.toArray();
    // 要插入的元素的个数。
    int numNew = a.length;
    if (numNew == 0)
        return false;

    Node<E> pred, succ;
    if (index == size) {
        // 如果index等于size，说明要把元素插入到链表尾部。
        // 既然是插入到尾部，那么新节点的下一个节点succ就是null，上一个节点pred就是当前的最后一个节点last，也就是说新节点插入到last节点后面。
        succ = null;
        pred = last;
    } else {
        // 否则新节点的下一个节点succ就是当前在index处的节点，而上一个节点就是当前index处节点的上一个节点。
        succ = node(index);
        pred = succ.prev;
    }

    // 遍历数组，为数组中的每个元素创建节点放入链表中。
    for (Object o : a) {
        @SuppressWarnings("unchecked") E e = (E) o;
        Node<E> newNode = new Node<>(pred, e, null);
        // 如果pred是null，说明当前要插入的节点是链表头。
        if (pred == null)
            first = newNode;
        else
            pred.next = newNode;
        pred = newNode;
    }

    // succ为null时对应的是上面index == size的情况。
    if (succ == null) {
        last = pred;
    } else {
        // 这里的pred等于最后一次创建的newNode。
        pred.next = succ;
        succ.prev = pred;
    }

    // 更新节点数量。
    size += numNew;
    // 类成员，用于检测并发修改。
    // protected transient int modCount;
    modCount++;
    return true;
}

// 检查索引是否越界。
private void checkPositionIndex(int index) {
    if (!isPositionIndex(index))
        throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
}

private boolean isPositionIndex(int index) {
    return index >= 0 && index <= size;
}

// Node是LinkedList中的内部类，一个Node就是双向链表中的一个节点。
private static class Node<E> {
    E item;
    // 指向下一个节点。
    Node<E> next;
    // 指向上一个节点。
    Node<E> prev;

    Node(Node<E> prev, E element, Node<E> next) {
        this.item = element;
        this.next = next;
        this.prev = prev;
    }
}

// 查找指定位置的节点。
Node<E> node(int index) {
    // 如果要查找的位置更靠近first，那么就从first开始往后搜索。
    if (index < (size >> 1)) {
        Node<E> x = first;
        for (int i = 0; i < index; i++)
            x = x.next;
        return x;
    } else {
        // 如果要查找的位置更靠近last，那么就从last开始往前搜索。
        Node<E> x = last;
        for (int i = size - 1; i > index; i--)
            x = x.prev;
        return x;
    }
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
    // last是类成员，指向链表的最后一个节点。
    // transient Node<E> last;
    final Node<E> l = last;
    // 创建一个新的节点，节点的prev指向最后一个节点。
    final Node<E> newNode = new Node<>(l, e, null);
    // 修改last指向刚才创建的节点。
    last = newNode;
    if (l == null)
        // 如果l为null，说明列表是空的，把first指向刚才创建的节点。
        // first是类成员，指向链表的第一个节点。
        // transient Node<E> first;
        first = newNode;
    else
        // 如果列表不是空的，那么就把原来的最后一个节点的next指向新创建的节点。
        l.next = newNode;
    size++;
    modCount++;
}
```

## add(int, E)

把元素插入到指定位置。

```java
public void add(int index, E element) {
    // 越界检查。
    checkPositionIndex(index);
    if (index == size)
        // 如果要插入的位置正好处在链表的末尾，那么就直接调用linkLast()方法，和add(E)一样。
        linkLast(element);
    else
        // 否则找到第index个节点，并把新的节点插入到它前面。
        linkBefore(element, node(index));
}

// 插入元素到指定节点前面。
void linkBefore(E e, Node<E> succ) {
    final Node<E> pred = succ.prev;
    final Node<E> newNode = new Node<>(pred, e, succ);
    succ.prev = newNode;
    if (pred == null)
        // 如果pred是null，说明succ是当前链表中第一个节点，在它之前插入新的节点后，新的节点就替代它成为了链表的第一个节点。
        first = newNode;
    else
        pred.next = newNode;
    size++;
    modCount++;
}
```

## get(int)

获取位于指定位置的元素。

```java
public E get(int index) {
    // 越界检查。
    checkElementIndex(index);
    // 通过上文的node()方法查找指定位置的节点并取出节点中的元素。
    return node(index).item;
}
```

## indexOf

获取指定元素第一次出现时的索引号。

```java
public int indexOf(Object o) {
    int index = 0;
    // 对于o是否为null分为两种情况。
    // 当o是null时，用==符号去匹配；当o不是null时，用equals()方法去匹配。
    // 以上两种情况都是从first节点开始向后遍历链表。
    if (o == null) {
        for (Node<E> x = first; x != null; x = x.next) {
            if (x.item == null)
                return index;
            index++;
        }
    } else {
        for (Node<E> x = first; x != null; x = x.next) {
            if (o.equals(x.item))
                return index;
            index++;
        }
    }
    return -1;
}
```

## lastIndexOf(Object)

获取指定元素最后一次出现的位置的索引号。

```java
// 和indexOf()方法类似，只是从last节点开始向前遍历。
public int lastIndexOf(Object o) {
    int index = size;
    if (o == null) {
        for (Node<E> x = last; x != null; x = x.prev) {
            index--;
            if (x.item == null)
                return index;
        }
    } else {
        for (Node<E> x = last; x != null; x = x.prev) {
            index--;
            if (o.equals(x.item))
                return index;
        }
    }
    return -1;
}
```

## contains(Object)

是否包含指定元素。

```java
// 直接调用indexOf()方法获取指定元素第一次出现的位置的索引号，如果返回-1则表示不存在该元素。
public boolean contains(Object o) {
    return indexOf(o) >= 0;
}
```

## set(int, E)

设置指定位置节点中的元素。

```java
public E set(int index, E element) {
    // 越界检查。
    checkElementIndex(index);
    // 查找位于指定索引处的节点。
    Node<E> x = node(index);
    E oldVal = x.item;
    // 更新节点中的元素。
    x.item = element;
    return oldVal;
}
```

## remove(Object)

从链表中删除第一个匹配的元素。

```java
public boolean remove(Object o) {
    // 如果目标对象是null那么用==匹配，否则用equals()方法匹配。
    // 查找到元素所在的节点后调用unlink()方法从链表中移除节点。
    if (o == null) {
        for (Node<E> x = first; x != null; x = x.next) {
            if (x.item == null) {
                unlink(x);
                return true;
            }
        }
    } else {
        for (Node<E> x = first; x != null; x = x.next) {
            if (o.equals(x.item)) {
                unlink(x);
                return true;
            }
        }
    }
    return false;
}

// 从链表中移除节点。
E unlink(Node<E> x) {
    final E element = x.item;
    final Node<E> next = x.next;
    final Node<E> prev = x.prev;

    if (prev == null) {
        // 如果prev是空，说明要移除的节点是链表中的第一个节点，因此要把first指向下一个节点。
        first = next;
    } else {
        // 把上一个节点的next指向下一个节点。
        prev.next = next;
        // 解除对上一个节点的引用。
        x.prev = null;
    }

    if (next == null) {
        // 如果next是空，说明要移除的节点是链表中的最后一个节点，因此要把last指向上一个节点。
        last = prev;
    } else {
        // 把下一个节点的prev指向上一个节点。
        next.prev = prev;
        // 解除对下一个节点的引用。
        x.next = null;
    }

    x.item = null;
    size--;
    modCount++;
    return element;
}
```

## clear()

清空链表。

```java
public void clear() {
    // 遍历链表，把每个节点的next和prev置为null以清除节点直接的相互引用，把节点中的元素置为null。
    for (Node<E> x = first; x != null; ) {
        Node<E> next = x.next;
        x.item = null;
        x.next = null;
        x.prev = null;
        x = next;
    }
    // 重置first和last。
    first = last = null;
    // 重置size。
    size = 0;
    modCount++;
}
```

## toArray()

转换为数组。

```java
public Object[] toArray() {
    // 创建一个数组。
    Object[] result = new Object[size];
    int i = 0;
    // 遍历链表把每个节点中的元素添加到数组中。
    for (Node<E> x = first; x != null; x = x.next)
        result[i++] = x.item;
    return result;
}
```