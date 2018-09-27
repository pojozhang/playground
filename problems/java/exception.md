# 异常

## Exception和Error的异同

- 相同点：两者都继承自`Throwable`。
- 不同点：`Exception`是可以预料的异常，开发者在捕获异常后可以进行处理，程序有可能从异常情况下恢复；`Error`表示程序出现严重问题，处于不正常的情况，通常是不可恢复的，经常表示JVM出现问题，比如`OutOfMemoryError`、`StackOverflowError`，我们不应该对`Error`进行异常捕获。

## Checked Exception和Unchecked Exception

Unchecked Exception是指`RuntimeException`及其子类，通常是开发者自身的问题，如`NullPointerException`、`IndexOutOfBoundsException`。

除此之外都是Checked Exception，比如`IOException`。

## NoClassDefFoundError和ClassNotFoundException



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