# clone()

`clone()`方法用于复制一个对象，该方法定义在`java.lang.Object`类中，因此所有的类都拥有该方法。

```java
protected native Object clone() throws CloneNotSupportedException;
```

下面看一个例子：

```java
public class Address {
    public String city;

    public Address(String city) {
        this.city = city;
    }
}

public class Person {
    public int age;
    public Address address;

    public Person(int age, Address address) {
        this.age = age;
        this.address = address;
    }
}

public static void main(String[] args) throws CloneNotSupportedException {
    Person source = new Person("name", new Address("Shanghai"));
    Person copy = (Person) source.clone();
}
```

以上代码在运行时会抛出`java.lang.CloneNotSupportedException`异常，这是因为`Person`类没有实现`Cloneable`接口。在我们做了如下修改后就能正确复制对象了。

```java
public class Person implements Cloneable
```

`clone()`方法在`Object`中用`protected`修饰，因此在子类中如果希望把该方法公开，那么在重写该方法时应该使用`public`进行修饰。

```java
// 在Person类中重写clone()方法
@Override
public Object clone() throws CloneNotSupportedException {
    return super.clone();
}
```

## 深复制还是浅复制

有关深复制和浅复制的概念可以前往[这里](shallow-copy-deep-copy.md)进行了解。对于`clone()`方法是一浅复制还是深复制，我们直接编码看一下：

```java
public static void main(String[] args) throws CloneNotSupportedException {
    Person source = new Person("name", new Address("Shanghai"));
    Person copy = (Person) source.clone();
    source.address.city = "Beijing";
    System.out.println(copy.address.city); //此处打印“Beijing”
}
```

以上代码最后打印结果是“Beijing”，说明复制后的对象中的`address`和源对象中的`address`引用同一对象，因此`clone()` **是浅复制**。

那我们能否修改`clone()`方法以实现深复制呢？答案是肯定的。

```java
@Override
public Object clone() throws CloneNotSupportedException {
    Person copy = (Person) super.clone();
    //针对对象类型的字段单独复制
    copy.address = new Address(this.address.city);
    return copy;
}
```

对于`address`字段我们进行单独复制，甚至如果`Address`类型实现了`Cloneable`接口我们也可以直接调用`address`的`clone()`方法进行复制，这样就做到了深复制。

这里需要注意的是，如果`address`字段被`final`修饰，那么深复制就不可行了，因为我们无法在`clone()`方法中对复制后的`Person`对象的`address`字段进行赋值。
