# ScheduledThreadPoolExecutor

![版本](https://img.shields.io/badge/java-11-blue.svg)

`ScheduledThreadPoolExecutor`是一个执行定时任务的线程池，它的类结构如下。

![](resources/scheduled-thread-pool-executor-1.png)

## ScheduledThreadPoolExecutor(int)

```java
public ScheduledThreadPoolExecutor(int corePoolSize) {
    super(corePoolSize, Integer.MAX_VALUE,
            DEFAULT_KEEPALIVE_MILLIS, MILLISECONDS,
            new DelayedWorkQueue());
}
```
