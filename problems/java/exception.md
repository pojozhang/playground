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

## 异常表

下面的`try-catch-finally`最常用的一种形式。

```java
public static void main(String[] args) {
    try {
        tryBlock();
    } catch (RuntimeException e) {
        catchBlock();
    } catch (Exception e) {
        catchBlock();
    } finally {
        finallyBlock();
    }
}

private static void tryBlock() {}

private static void catchBlock() {}

private static void finallyBlock() {}
```

对应字节码如下。

```java
public static void main(java.lang.String[]);
    Code:
       0: invokestatic  #2                  // Method tryBlock:()V
       3: invokestatic  #3                  // Method finallyBlock:()V
       6: goto          35
       9: astore_1
      10: invokestatic  #5                  // Method catchBlock:()V
      13: invokestatic  #3                  // Method finallyBlock:()V
      16: goto          35
      19: astore_1
      20: invokestatic  #5                  // Method catchBlock:()V
      23: invokestatic  #3                  // Method finallyBlock:()V
      26: goto          35
      29: astore_2
      30: invokestatic  #3                  // Method finallyBlock:()V
      33: aload_2
      34: athrow
      35: return
    Exception table:
       from    to  target type
           0     3     9   Class java/lang/RuntimeException
           0     3    19   Class java/lang/Exception
           0     3    29   any
           9    13    29   any
          19    23    29   any
```

从字节码中我们可以明显看到`finally`块在每一种可能执行的路径中都复制了一份，这就是`finally`块总是会执行的原因。

`try-catch`机制是通过异常表实现的，字节码中的`Exception table`部分就是异常表，它决定了异常发生后代码的跳转逻辑。
比如，第一行的`0     3     9   Class java/lang/RuntimeException`表示如果在[0, 3)区间内的字节码有`java/lang/RuntimeException`类型的异常抛出那么就跳到第9行（包括第9行）的字节码继续执行。`type`为`any`的行表示在指定区间内遇到任何类型的异常都进行跳转。

我们对`main()`方法稍作修改，引入一个语法糖，使得一个`catch`块捕获多种异常类型。

```java
public static void main(String[] args) {
    try {
        tryBlock();
    } catch (IllegalArgumentException |  ArrayIndexOutOfBoundsException e) {
        catchBlock();
    } finally {
        finallyBlock();
    }
}
```

它的字节码如下。

```java
public static void main(java.lang.String[]);
    Code:
       0: invokestatic  #2                  // Method tryBlock:()V
       3: invokestatic  #3                  // Method finallyBlock:()V
       6: goto          25
       9: astore_1
      10: invokestatic  #6                  // Method catchBlock:()V
      13: invokestatic  #3                  // Method finallyBlock:()V
      16: goto          25
      19: astore_2
      20: invokestatic  #3                  // Method finallyBlock:()V
      23: aload_2
      24: athrow
      25: return
    Exception table:
       from    to  target type
           0     3     9   Class java/lang/IllegalArgumentException
           0     3     9   Class java/lang/ArrayIndexOutOfBoundsException
           0     3    19   any
           9    13    19   any
```

可以看到异常表中对于这两种异常类型生成了两个条目。

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

从抛出的异常信息可以看到，第一次的异常信息`RuntimeException A`被吞掉了，只能看到第二次抛出的异常信息。以下是编译后的字节码。

```java
 public static void main(java.lang.String[]);
    Code:
       0: new           #2                  // class java/lang/RuntimeException
       3: dup
       4: ldc           #3                  // String RuntimeException A
       6: invokespecial #4                  // Method java/lang/RuntimeException."<init>":(Ljava/lang/String;)V
       9: athrow
      10: astore_1
      11: new           #2                  // class java/lang/RuntimeException
      14: dup
      15: ldc           #5                  // String RuntimeException B
      17: invokespecial #4                  // Method java/lang/RuntimeException."<init>":(Ljava/lang/String;)V
      20: athrow
    Exception table:
       from    to  target type
           0    11    10   any
}
```

异常表显示在[0, 11)这个区间内抛出任何异常都直接跳到第10行继续执行，而第10行后的代码（对应`finally`块中的代码）只是简单创建了一个新的异常对象并抛出，并没有对之前的异常进行任何处理，因此第一个异常信息就被吞掉了。

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

再看第二个例子。

```java
public static void main(String[] args) {
    System.out.println(f()); //此处打印“2”。
}

static int f() {
    try {
        return 0;
    } catch (Exception e) {
        return 1;
    } finally {
        return 2;
    }
}
```

以上代码最后打印结果是2，说明最后`return`出去的是`finally`块里的值。以下是字节码。

```java
static int f();
    Code:
       0: iconst_0
       1: istore_0
       2: iconst_2
       3: ireturn
       4: astore_0
       5: iconst_1
       6: istore_1
       7: iconst_2
       8: ireturn
       9: astore_2
      10: iconst_2
      11: ireturn
    Exception table:
       from    to  target type
           0     2     4   Class java/lang/Exception
           0     2     9   any
           4     7     9   any
```

可以看到所有的`ireturn`指令前都紧跟着一句`iconst_2`，说明确实是返回`finally`块中的值。
