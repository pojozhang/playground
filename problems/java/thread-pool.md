# 线程池

![版本](https://img.shields.io/badge/java-10-red.svg)

## ExecutorService

JDK通过`ExecutorService`接口定义了线程池，通常我们用以下几个`Executors`类中的工厂方法创建`ExecutorService`对象。

- newSingleThreadExecutor()

创建一个单线程的线程池，按任务提交的先后顺序依次执行。

- newCachedThreadPool()

该线程池可以按需创建或重用线程，并且当线程长时间闲置后会对这些线程进行回收。

- newFixedThreadPool(int)

创建一个拥有固定线程数的线程池。

- newScheduledThreadPool(int)

创建一个可以周期性执行任务的线程池。

## ThreadPoolExecutor

`Executors`类中创建的几种线程池有一部分是对`ThreadPoolExecutor`对象的封装，下面我们来看下`ThreadPoolExecutor`相关的内容。

`ThreadPoolExecutor`类提供了一个可以灵活定制的线程池，其层次结构如下。

![](resources/thread_pool_1.png)

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

线程的存活时间。当线程池中线程的空闲时间超过了存活时间，并且线程池中线程的数量超过`corePoolSize`，那么这些空闲的线程会被终止。

- unit

存活时间的计量单位。

- workQueue

工作队列。用来存储提交到线程池中的任务。

- threadFactory

用来创建线程的工厂。

- handler

当工作队列饱和时，`ThreadPoolExecutor`类会使用`handler`做进一步的处理。

### 创建新线程的时机

当我们向线程池提交任务时，如果此时工作队列已满，并且线程池中的线程数量还没有达到`maximumPoolSize`，那么线程池就会用`threadFactory`创建一个新的线程。

下面的代码创建了一个初始线程数为2，最大线程数为4的线程池，我们向线程池连续提交6个任务，每个任务都会阻塞10秒。

```java
public static void main(String[] args) {
    int corePoolSize = 2; // 线程池中线程数的下限。
    int maximumPoolSize = 4; // 线程池中线程数的上限。
    int taskCount = 6; // 任务数量。
    int capacity = 2; // 工作队列容量。

    ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
            0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(capacity));

    for (int i = 0; i < taskCount; i++) {
        executor.submit(() -> {
            System.out.println(Thread.currentThread().getId());
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
```

当工作队列容量为2时，线程池会创建4个线程池执行任务，这是因为在提交第1、第2个任务时线程池会创建两个线程，达到`corePoolSize`指定的值；提交第3、第4个任务时线程池会把任务放到工作队列中；当提交第5个任务时，由于队列已满，线程池会创建一个线程并从工作队列中取出一个任务执行，再把第5个任务放入队列中；当提交第6个任务时，队列还是满的，线程池会再创建一个线程并从工作队列中取出一个任务执行，再把第6个任务放入队列中。此时线程池中的线程数正好达到`maximumPoolSize`。

如果我们把队列容量改为1，那么当我们提交第6个任务时会抛出`java.util.concurrent.RejectedExecutionException`异常，这是因为工作队列已满。

而当我们把队列容量扩大为10时，我们会发现至始至终都只会有2个线程进行工作，这是因为任务数量是6，永远无法让工作队列饱和。

### 关闭线程池

通常我们用`shutdown()`或`shutdownNow()`方法关闭线程池，一旦线程池被关闭，就无法再提交新的任务，这两个方法的区别是`shutdown()`在终止线程池之前会执行已提交的任务，包括在工作队列中等待的任务；`shutdownNow()`方法会试图通过线程的`interrupt()`方法中断正在执行的任务，对于已经在工作队列中的还未执行的任务则全部取消。

### 饱和策略

当我们向线程池提交一个新的任务而线程池的工作队列已经饱和时会调用`RejectedExecutionHandler`对象执行饱和策略。

`RejectedExecutionHandler`是一个接口，它有以下几种实现。

- AbortPolicy

默认的策略，直接抛出`RejectedExecutionException`异常。

- DiscardPolicy

丢弃被拒绝的任务。

- DiscardOldestPolicy

丢弃工作队列中最旧的任务，然后重新提交被拒绝的任务。

- CallerRunsPolicy

如果线程池没有被关闭，那么当前线程（饱和策略的执行线程）直接执行任务（调用`Runnable`的`run()`方法）。
