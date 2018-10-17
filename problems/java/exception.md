# 异常

## Exception和Error的异同

- 相同点：两者都继承自`Throwable`。
- 不同点：`Exception`是可以预料的异常，开发者在捕获异常后可以进行处理，程序有可能从异常情况下恢复；`Error`表示程序出现严重问题，处于不正常的情况，通常是不可恢复的，经常表示JVM出现问题，比如`OutOfMemoryError`、`StackOverflowError`，我们不应该对`Error`进行异常捕获。

## Checked Exception和Unchecked Exception

Unchecked Exception是指`RuntimeException`及其子类，通常是开发者编码错误导致的问题，如空指针异常`NullPointerException`、数组越界异常`IndexOutOfBoundsException`。

除此之外都是Checked Exception，比如`IOException`。

## NoClassDefFoundError

该异常是指在运行时找不到某个类的定义但是可以被正确编译。

## ClassNotFoundException

当我们使用反射去显式加载一个类而找不到该类时就会抛出这个异常，这是一个Checked Exception。

```java
Class.forName("UnknownClass");
```

## finally块在什么情况下不会执行

在程序被中断的情况下finally块不会被执行，比如设备断电、进程被杀死。
以下代码也有中断程序的效果：

```java
try {
    System.exit(0);
} finally {
    System.out.println("finally");
}
```

## java.lang.Throwable#fillInStackTrace()

该方法用于把当前异常的堆栈信息替代原来的堆栈信息。

```java
public static void main(String[] args) {
    try {
        f();
    } catch (Exception e) {
        throw e;
    }
}

private static void f() {
    throw new RuntimeException();
}
```

以上代码在`main()`方法中捕获`f()`方法抛出的异常并重新抛出，此时堆栈信息如下：

```java
Exception in thread "main" java.lang.RuntimeException
	at playground.ExceptionTest.f(ExceptionTest.java:18)
	at playground.ExceptionTest.main(ExceptionTest.java:11)
```

我们对以上代码进行修改，对于`main()`中捕获到的异常对象我们调用它的`fillInStackTrace()`方法。

```java
public static void main(String[] args) {
    try {
        f();
    } catch (Exception e) {
        throw (RuntimeException) e.fillInStackTrace();
    }
}
```

此时堆栈信息如下所示：

```java
Exception in thread "main" java.lang.RuntimeException
	at playground.ExceptionTest.main(ExceptionTest.java:13)
```

可以看到已经没有了`f()`方法的调用信息。

## 丢失的异常信息

以下代码中首次抛出的异常信息会丢失。

```java
public static void main(String[] args) {
    try {
        throw new RuntimeException("RuntimeException A");
    } finally {
        throw new RuntimeException("RuntimeException B");
    }
}
```

```java
Exception in thread "main" java.lang.RuntimeException: RuntimeException B
	at playground.ExceptionTest.main(ExceptionTest.java:9)
```

## finally生成的字节码



## finally和return的执行顺序

我们都知道`return`语句在`finally`块执行后才会执行，那么为什么以下代码中，我们对局部变量`x`自增后再`return`，输出结果却是自增前的0？

```java
public static void main(String[] args) {
    System.out.println(f()); //此处输出“0”。
}

static int f() {
    int x = 0;
    try {
        return x;
    } finally {
        x++;
    }
}
```

以下是`f()`方法在编译后生成的字节码。

```java
static int f();
    Code:            //栈           局部变量表            描述
       0: iconst_0   //[0]          []                  整数常量0入栈。
       1: istore_0   //[]           [0]                 弹出栈顶元素（上一步中入栈的0），并放入局部变量表中索引为0的位置。
       2: iload_0    //[0]          [0]                 局部变量表中索引为0的位置的变量入栈。
       3: istore_1   //[]           [0, 0]              弹出栈顶元素（上一步中的变量），并放入局部变量表中索引为1的位置。
       4: iinc 0, 1  //[]           [1, 0]              局部变量表中索引为0的位置的变量自增1。
       7: iload_1    //[0]          [1, 0]              局部变量表中索引为0的位置的变量入栈。
       8: ireturn    //[0]          [1, 0]              返回栈顶元素的值。
       9: astore_2   //[]           [0, 0, Throwable]   弹出栈顶元素（捕获的异常对象），并放入局部变量表中索引为2的位置。
      10: iinc 0, 1  //[]           [1, 0, Throwable]   局部变量表中索引为0的位置的变量自增1。
      13: aload_2    //[Throwable]  [1, 0, Throwable]   局部变量表中索引为2的位置的变量入栈。
      14: athrow     //[]           [1, 0, Throwable]   弹出栈顶元素并抛出元素表示的异常。
    Exception table:
       from    to  target type
           2     4     9   any
```

以上代码的执行路径有2条。

1. 正常路径是0->8。
2. 异常路径是0->3, 9->14。

从（7）可以看到在正常情况下最后`return`出去的值就是局部变量表中索引为1的位置的变量的值。从（1）-（3）可以看到该变量拷贝自局部变量表中索引为0的位置的变量，并且之后的字节码中都没有对其进行过修改。（4）和（10）都是`finally`块中的语句，分别在正常情况和异常情况下执行，对局部变量表中索引为0的位置的变量进行自增操作。因此我们可以看到`return`出去的变量和进行自增操作的变量是不同的，在`finally`块执行前返回值就已经确定了。