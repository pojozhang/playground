# 异常

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