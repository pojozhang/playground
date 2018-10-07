# 多态

## 静态绑定

静态绑定就是在编译期间就确定了是要访问对象

```java
class SuperClass {

    String field = "SuperClass";
}

class SubClass extends SuperClass {

    String field = "SubClass";
}
```

## 动态绑定