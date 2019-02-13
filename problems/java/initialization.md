# 对象的初始化

## 以下代码的执行顺序是什么？

```java
// 基类。
class SuperClass {

    static String staticField = print("SuperClass.staticField");

    static {
        print("SuperClass.staticBlock");
    }

    String field = print("SuperClass.field");

    {
        print("SuperClass.block");
    }

    static String print(String string) {
        System.out.println(string);
        return string;
    }

    SuperClass() {
        print("SuperClass()");
    }
}

// SuperClass的一个子类。
class SubClass extends SuperClass {

    static String staticField = print("SubClass.staticField");

    static {
        print("SubClass.staticBlock");
    }

    String field = print("SubClass.field");

    {
        print("SubClass.block");
    }

    SubClass() {
        print("SubClass()");
    }
}

public static void main(String[] args) {
    new SubClass();
}
```

代码执行结果：

```java
SuperClass.staticField
SuperClass.staticBlock
SubClass.staticField
SubClass.staticBlock
SuperClass.field
SuperClass.block
SuperClass()
SubClass.field
SubClass.block
SubClass()
```

因此我们可以得到结论，一个对象的初始化顺序如下：

1. 基类的静态字段和静态代码块，两者按代码先后顺序执行。
2. 子类的静态字段和静态代码块，两者按代码先后顺序执行。
3. 基类的非静态字段和代码块，两者按代码先后顺序执行。
4. 基类的构造方法。
5. 子类的非静态字段和静态代码块，两者按代码先后顺序执行。
6. 子类的构造方法。

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
