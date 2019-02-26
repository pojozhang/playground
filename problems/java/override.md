# 重写

当子类对父类中的方法进行重写时要遵循以下几点：

1. 子类方法的返回值范围小于等于父类，如父类返回`Object`，那么子类可以返回`Integer`；
2. 子类方法抛出的异常范围小于父类或者不抛出异常，如父类抛出`Exception`那么子类可以抛出`RuntimeException`；
3. 子类方法修饰符的范围大于或等于父类，如父类方法是`protected`，那么子类方法可以是`public`；
4. 当父类方法被`private`或`final`修饰时子类不能重写该方法。
5. 子类进行重写时，方法的参数类型必须和原方法完全一致。

```java
public class Parent {
    protected Object method() throws Exception {
        return new Object();
    }
}

public class Child extends Parent {
    @Override
    public Integer method() throws RuntimeException {
        return 1;
    }
}
```

## 构造方法能否被重写

构造方法不能被重写，只能被重载，因为构造方法不会被子类继承。

```java
class SuperClass {
    SuperClass() throws IOException {
        System.out.println("SuperClass");
    }
}

class SubClass extends SuperClass {
}
```

以上代码无法通过编译，因为子类`SubClass`的默认构造方法等价于：

```java
SubClass() {
    super();
}
```

而基类的构造方法抛出的是`IOException`，这是一个Checked Exception，因此子类只有以下两种选择：

方法一：在构造方法签名上显式抛出异常。

```java
SubClass() throws IOException {
    super(); //此句也可省略。
}
```

方法二：通过捕获基类构造方法抛出的异常，从而在子类构造方法中不抛出异常，但是事实上这种方法无法通过编译。

```java
//无法通过编译
SubClass() {
    try {
        super();
    } catch (IOException e) {
    }
}
```

错误信息如下。

```java
Error:(24, 18) java: 对super的调用必须是构造器中的第一个语句
```

同样的道理，以下代码也无法编译，因为基类构造方法抛出的是`IOException`，它无法转换成`ConnectException`(它是`IOException`的一个子类)。

```java
SubClass() throws ConnectException {
}
```

但是以下代码可以通过编译，因为`IOException`是`Exception`的子类。

```java
SubClass() throws Exception {
}
```
