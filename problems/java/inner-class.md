# 内部类

内部类就是把一个类定义在另一个类的内部。

```java
class OuterClass {
    class InnerClass {
    }
}
```

一个内部类的对象依赖于一个已存在的外部类的对象，因此我们必须通过一个外部类对象来创建内部类对象。创建的内部类对象拥有对外部类对象的引用。

```java
public static void main(String[] args) {
    OuterClass outer = new OuterClass();
    // 通过outer对象创建inner对象。
    OuterClass.InnerClass inner = outer.new InnerClass();
}
```

在一个内部类对象中`this`指的是内部类对象本身，如果要访问外部类对象，需要加上外部类的类名，如`OuterClass.this`。内部类对象可以访问所有外部类对象中的字段（包括private字段）。

```java
class OuterClass {

    private String field = "OuterClass";

    class InnerClass {
        private String field = "InnerClass";

        void printInnerField() {
            System.out.println(this.field);
        }

        void printOuterField() {
            System.out.println(OuterClass.this.field);
        }
    }
}

public static void main(String[] args) {
    OuterClass outer = new OuterClass();
    OuterClass.InnerClass inner = outer.new InnerClass();
    inner.printInnerField(); //此处打印“InnerClass”。
    inner.printOuterField(); //此处打印“OuterClass”。
}
```

内部类和字段一样也可以被`private`,`protected`,`public`等修饰（或者默认）且含义相同。

内部类 **不允许** 有静态变量或静态方法。

内部类 **允许** 多层嵌套。

```java
class OuterClass {
    class MiddleClass {
        class InnerClass {
        }
    }
}

public static void main(String[] args) {
    OuterClass outerClass = new OuterClass();
    MiddleClass middleClass = outerClass.new MiddleClass();
    InnerClass innerClass = middleClass.new InnerClass();
}
```

## 匿名类

匿名类就是没有名字的类，下面是一个实现了`Runnable`接口的匿名类。

```java
new Runnable() {
    @Override
    public void run() {
    }
};
```

### 匿名类中的final

以下代码试图在实现了`Runnable`接口的匿名类中对外部局部变量`object`重新赋值。

```java
public class AnonymousClass {

    public static void main(String[] args) {
        Object object = new Object();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                object = new Object();
                System.out.println(object);
            }
        };
        runnable.run();
    }
}
```

以上代码无法通过编译。

```java
AnonymousClass.java:8: error: local variables referenced from an inner class must be final or effectively final
                object = new Object();
                ^
```

原因是一个被内部类引用的外部局部变量必须是`final`的或者是等效于`final`的变量，因此我们无法对`object`变量重新赋值，因为它必须等效于被`final`修饰的变量。

我们对以上代码进行修改。

```java
public static void main(String[] args) {
    final Object object = new Object(); //此处的final在JDK8之后也可以省略，因为下面的匿名类中没有修改object对象，因此和final等效。
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            System.out.println(object);
        }
    };
    runnable.run();
}
```

我们对`AnonymousClass`进行反编译查看编译器处理过代码。

```java
class AnonymousClass$1 implements Runnable {

    AnonymousClass$1(Object var1) {
        this.val$object = var1;
    }

    public void run() {
        System.out.println(this.val$object);
    }
}
```

可以看到编译器为匿名类生成了一个叫做`AnonymousClass$1`的类，外部局部变量是通过构造函数传进来的，又因为java的引用是按值传递的，因此如果我们在内部类中修改`object`的引用是无法影响外部局部变量`object`的，语言设计者为了防止混淆所以强制要求这种情况下的局部变量必须由`final`修饰或等效于`final`。

但是如果`object`是类的字段那么就不需是`final`的，以下代码编译通过。

```java
public class AnonymousClass {

    Object object = new Object();

    void run() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                object = new Object();
            }
        };
        runnable.run();
    }
}

public static void main(String[] args) {
    new AnonymousClass().run();
}
```

查看反编译后的代码。

```java
class AnonymousClass$1 implements Runnable {

    AnonymousClass$1(AnonymousClass var1) {
        this.this$0 = var1;
    }

    public void run() {
        this.this$0.object = new Object();
    }
}
```

我们可以看到这个时候内部类拥有对外部对象的引用，因此可以修改`object`字段，也就不存在局部变量中出现的混淆问题了。

## 嵌套类