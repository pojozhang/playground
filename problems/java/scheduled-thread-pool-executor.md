# ScheduledThreadPoolExecutor

![版本](https://img.shields.io/badge/java-8-blue.svg)

```java
public ScheduledThreadPoolExecutor(int corePoolSize) {
    super(corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS,
            new DelayedWorkQueue());
}
```
