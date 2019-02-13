# Collections

![版本](https://img.shields.io/badge/java-10-blue.svg)

## nCopies(int, T)

生成一个包含指定数目对象副本的列表。以下代码生成了一个包含5个`Person`对象副本的列表，每个元素都是对同一个`Person`对象的引用。

```java
class Person {

    public int age;

    public Person(int age) {
        this.age = age;
    }
}

public static void main(String[] args) {
    List<Person> list = Collections.nCopies(5, new Person(18));
}
```

该方法返回的列表的类型是`java.util.Collections.CopiesList`，它是一个不可变对象，一旦初始化完成，不支持添加新的元素。

```java
private static class CopiesList<E>
    extends AbstractList<E>
    implements RandomAccess, Serializable
{
    final int n;
    final E element;

    CopiesList(int n, E e) {
        assert n >= 0;
        this.n = n;
        element = e;
    }

    // 省略其它代码。
}
```

## fill(List<? super T>, T)

用指定的元素替代列表中所有的元素。

```java
public static <T> void fill(List<? super T> list, T obj) {
    int size = list.size();
    // 如果列表长度小于阈值或者列表实现了随机访问接口，那么就调用java.util.List#set方法替代之前的元素。
    if (size < FILL_THRESHOLD || list instanceof RandomAccess) {
        for (int i=0; i<size; i++)
            list.set(i, obj);
    } else {
        // 否则就用迭代器替换之前的元素。
        ListIterator<? super T> itr = list.listIterator();
        for (int i=0; i<size; i++) {
            itr.next();
            itr.set(obj);
        }
    }
}
```

## shuffle(List<?>, Random)