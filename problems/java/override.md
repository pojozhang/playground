# 重写

当子类对父类中的方法进行重写时要遵循以下几点：

1. 子类方法的返回值范围小于等于父类，如父类返回`Object`，那么子类可以返回`Integer`；
2. 子类方法抛出的异常范围小于父类，如父类抛出`Exception`那么子类可以抛出`RuntimeException`；
3. 子类方法修饰符的范围大于或等于父类，如父类方法是`protected`，那么子类方法可以是`public`；
4. 当父类方法被`private`或`final`修饰时子类不能重写该方法。

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
