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
