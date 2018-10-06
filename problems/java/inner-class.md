# 内部类

内部类就是把一个类的定义在另一个类的内部。

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
    inner.printInnerField(); // 此处打印 “InnerClass”
    inner.printOuterField(); // 此处打印 “OuterClass”
}
```

内部类和字段一样也可以被`private`,`protected`,`public`等修饰（或者默认）且含义相同。

内部类 **不允许** 有静态变量或静态方法，**允许** 多层嵌套。

## 匿名内部类

匿名类就是没有名字的类，下面是一个实现了`Runnable`接口的匿名类。

```java
new Runnable() {
    @Override
    public void run() {
    }
};
```

## 嵌套类