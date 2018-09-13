# Comparable和Comparator

## Comparable

一个类如果实现了`Comparable`接口就表示该类是可比较的，我们可以用`Collections.sort()`或`Arrays.sort()`对该类的集合或数组进行排序：

```java
public interface Comparable<T> {
    public int compareTo(T o);
}
```

该接口只有一个方法，如果`this.compareTo(o)`返回负值就表示`this`比`o`小，返回零则表示两个对象相等，否则表示`this`比`o`大。`Collections.sort()`或`Arrays.sort()`按从小到大对元素进行排序。

下面是一个例子：

```java
public class Film implements Comparable<Film> {

    private int score;

    public Film(int score) {
        this.score = score;
    }

    @Override
    public int compareTo(Film o) {
        return this.score - o.score;
    }
}

public static void main(String[] args) {
    List<Film> films = new ArrayList<>();
    films.add(new Film(65));
    films.add(new Film(60));
    films.add(new Film(80));
    Collections.sort(films);//[65,60,80]->[60,65,80]
}
```

如果要从大到小排列，我们可以用`Collections.reverse()`方法，但该方法只能用于集合，JDK没有在Arrays类中实现与之对应的方法。
由于排序逻辑是写在类的内部，因此`Comparable`也称内部比较器。

## Comparator

`Comparable`有两个很明显的缺陷，代码必须写在类的内部，并且只能按照固定的规则进行排序。如果我们无法修改类的内部或者有多种排序规则，那么`Comparable`接口就无能为力了。
`Comparator`作为外部比较器解决了这个问题。

```java
public interface Comparator<T> {
    int compare(T o1, T o2);
    boolean equals(Object obj);
    // other methods
}
```

`Comparator`接口有两个需要实现类自己去实现，一个是`compare()`，一个是`equals()`，由于`Object`类已经实现了第二个方法，而它又是所有类的父类，因此我们无需实现`equals()`方法。`compare()`方法和`Comparable`的`compareTo`非常类似，如果`compare(o1,o2)`返回负值就表示`o1`比`o2`小，返回零则表示两个对象相等，否则表示`o1`比`o2`大。

我们用`Comparator`接口改写上面的代码：

```java
public static void main(String[] args) {
    List<Film> films = new ArrayList<>();
    films.add(new Film(65));
    films.add(new Film(60));
    films.add(new Film(80));
    films.sort((o1, o2) -> o1.score - o2.score);//[65,60,80]->[60,65,80]
}
```