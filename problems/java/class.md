# 类型

## 载入

一个类在首次被载入时会进行初始化，这时会执行类中的静态代码。

```java
public class ClassTest {

    public static void main(String[] args) throws ClassNotFoundException {
        System.out.println("ClassTest");
        Class.forName("ClassTest$A"); //首次载入内部类打印“Class A”。
        Class.forName("ClassTest$A"); //再次载入内部类不打印。
    }

    public static class A {
        static {
            System.out.println("Class A");
        }
    }
}
```

在以`.class`的形式来引用一个`Class`对象时不会初始化。

```java
public static void main(String[] args) {
    System.out.println(A.class);
}
```

在访问一个`static final`的常量时，如果它在编译时就能被确定，那么当我们首次访问它时也不会对类进行初始化。

```java
public static void main(String[] args) {
    System.out.println(A.FIELD); //此处打印“1”。
}

public static class A {
    public static final int FIELD = 1;

    static {
        System.out.println("Class A"); // 此句不会被执行。
    }
}
```

但是如果该常量不能在编译时被确定，那么类会被初始化。我们把`A`中的`FIELD`常量进行如下修改，此时`A`中的静态代码块就会被执行。

```java
public static final int FIELD = new Random().nextInt(100);
```

## java.lang.Class#getSuperclass()

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

## java.lang.Class#isAssignableFrom(Class<?>)

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