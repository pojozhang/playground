# 多态

## 静态绑定和动态绑定

绑定是指一个方法的调用与一个类关联起来，比如以下代码就是把`add`方法和`ArrayList`关联起来。

```java
List<Integer> list = new ArrayList<>();
list.add(0);
```

绑定又分为静态绑定和动态绑定。静态绑定是指在编译期间就确定了的绑定，比如以下的静态方法调用。

```java
List.of(0);
```

Java中被`final`，`static`，`private`修饰的方法以及构造方法都是静态绑定。

```java
class SuperClass {

    String field = "SuperClass";
}

class SubClass extends SuperClass {

    String field = "SubClass";
}
```
