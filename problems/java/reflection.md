# 反射

## Class

### getSuperclass()

该方法用于获得一个类的基类，需要注意的是在编写代码时并不能直接获得基类的类型。以下代码的`main()`方法中，我们只能把`superclass`声明为是`SubClass`的基类，而不能明确指定为`SuperClass`。

```java
public static class SuperClass {
}

public static class SubClass extends SuperClass {
}

public static void main(String[] args) {
    Class<? super SubClass> superclass = SubClass.class.getSuperclass();
    // 若改成 SuperClass superclass = SubClass.class.getSuperclass(); 则编译不通过。
}
```

### isAssignableFrom(Class<?>)

判断一个类是不是另一个类的基类或接口（或者两个类相同）。

```java
public static void main(String[] args) {
    System.out.println(SuperClass.class.isAssignableFrom(SubClass.class)); //true。
    System.out.println(SubClass.class.isAssignableFrom(SuperClass.class)); //false。
}

public static class SuperClass {
}

public static class SubClass extends SuperClass {
}
```

### getMethods()

获取所有`public`的方法，**包括**静态方法和从基类中继承过来的方法。

### getDeclaredMethods()

获取所有方法，但**不包括**从基类中继承过来的方法。
