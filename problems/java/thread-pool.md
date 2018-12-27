# 线程池

## ThreadPoolExecutor

`ThreadPoolExecutor`类提供了一个可以灵活定制的线程池，我们通常不会直接在代码中创建`ThreadPoolExecutor`对象，而是使用`Executors`类的工厂方法间接的创建一个线程池，这些通过`Executors`类创建的线程池都基于`ThreadPoolExecutor`类。

`ThreadPoolExecutor`类最基本的一个构造方法如下所示。

```java
public ThreadPoolExecutor(int corePoolSize,
                          int maximumPoolSize,
                          long keepAliveTime,
                          TimeUnit unit,
                          BlockingQueue<Runnable> workQueue,
                          ThreadFactory threadFactory,
                          RejectedExecutionHandler handler)
```

- corePoolSize

线程池的基本大小，线程池中的线程数量的下限。

- maximumPoolSize

线程池中的线程数量的上限。

- keepAliveTime

- unit

- workQueue

- threadFactory

- handler

## newSingleThreadExecutor()

```java
public static ExecutorService newSingleThreadExecutor() {
    return new FinalizableDelegatedExecutorService
        (new ThreadPoolExecutor(1, 1,
                                0L, TimeUnit.MILLISECONDS,
                                new LinkedBlockingQueue<Runnable>()));
}
```

## newCachedThreadPool()

```java
public static ExecutorService newCachedThreadPool() {
    return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                  60L, TimeUnit.SECONDS,
                                  new SynchronousQueue<Runnable>());
}
```

## newFixedThreadPool(int)

```java
public static ExecutorService newFixedThreadPool(int nThreads) {
    return new ThreadPoolExecutor(nThreads, nThreads,
                                  0L, TimeUnit.MILLISECONDS,
                                  new LinkedBlockingQueue<Runnable>());
}
```

### newScheduledThreadPool(int)
