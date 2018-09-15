# clone()

```java
protected native Object clone() throws CloneNotSupportedException;
```

`clone()`方法用于复制一个对象，该方法定义在`java.lang.Object`类中，因此所有的类都拥有该方法。该方法在`Object`中用`protected`修饰，因此在子类中如果希望把该方法公开，那么在重写该方法时应该使用`public`进行修饰。

下面看一个例子：

```java
public class Address {
    public String city;

    public Address(String city) {
        this.city = city;
    }
}

public class Person {
    public String name;
    public Address address;

    public Person(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public Person(Person person) {
        this.name = person.name;
        this.address = person.address;
    }
}

public static void main(String[] args) throws CloneNotSupportedException {
    Person source = new Person("name", new Address("Shanghai"));
    Object copy = (Person) source.clone();
}
```

以上代码在运行时会抛出`java.lang.CloneNotSupportedException`异常，这是因为`Person`类没有实现`Cloneable`接口。在我们做了如下修改后就能正确复制对象了。

```java
public class Person implements Cloneable
```