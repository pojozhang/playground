# ScheduledThreadPoolExecutor

![版本](https://img.shields.io/badge/java-11-blue.svg)

`ScheduledThreadPoolExecutor`是一个执行定时任务的线程池，它的类结构如下。

![](resources/scheduled-thread-pool-executor-1.png)

## ScheduledThreadPoolExecutor(int)

从构造方法中可以看到，`ScheduledThreadPoolExecutor`使用了`DelayedWorkQueue`队列。

```java
public ScheduledThreadPoolExecutor(int corePoolSize) {
    super(corePoolSize, Integer.MAX_VALUE,
          DEFAULT_KEEPALIVE_MILLIS, MILLISECONDS,
          new DelayedWorkQueue());
}
```

## schedule(Runnable, long, TimeUnit)

`schedule()`方法用于延迟执行一次性的任务。

```java
public ScheduledFuture<?> schedule(Runnable command,
                                   long delay,
                                   TimeUnit unit) {
    if (command == null || unit == null)
        throw new NullPointerException();
    // 构建一个RunnableScheduledFuture对象，包含触发时间，序号等信息。
    RunnableScheduledFuture<Void> t = decorateTask(command,
        new ScheduledFutureTask<Void>(command, null,
                                      triggerTime(delay, unit),
                                      sequencer.getAndIncrement()));
    delayedExecute(t);
    return t;
}

private void delayedExecute(RunnableScheduledFuture<?> task) {
    // 如果线程池已经关闭了，那么执行拒绝策略。
    if (isShutdown())
        reject(task);
    else {
        // 把任务放入队列中，这里的队列就是DelayedWorkQueue。
        super.getQueue().add(task);
        // 判断当前任务是否可以执行。
        // 如果不能执行，那么尝试从队列中删除任务。
        // 如果删除成功那么就把任务标为已取消。
        if (!canRunInCurrentRunState(task) && remove(task))
            task.cancel(false);
        else
            // 否则开始任务。
            ensurePrestart();
    }
}

boolean canRunInCurrentRunState(RunnableScheduledFuture<?> task) {
    // 如果线程池没有被关闭，那么任务可以执行。
    if (!isShutdown())
        return true;
    // 如果线程池已经进入停止状态，那么任务不能执行。
    if (isStopped())
        return false;
    // 否则由以下两个参数决定能否执行任务。
    // 默认continueExistingPeriodicTasksAfterShutdown是false，executeExistingDelayedTasksAfterShutdown是true。
    return task.isPeriodic()
        ? continueExistingPeriodicTasksAfterShutdown
        : (executeExistingDelayedTasksAfterShutdown
            || task.getDelay(NANOSECONDS) <= 0);
}

void ensurePrestart() {
    // 获取当前工作线程的数量。
    int wc = workerCountOf(ctl.get());
    // 增加工作线程。
    if (wc < corePoolSize)
        addWorker(null, true);
    else if (wc == 0)
        addWorker(null, false);
}
```

`addWorker()`方法主要作用是创建`Worker`对象，每个对象关联一个线程，不断从工作队列中取出任务并执行，详细解析可以查看[《ThreadPoolExecutor》](https://github.com/pojozhang/playground/blob/master/problems/java/thread-pool-executor.md#submitrunnable)一文，不再赘述。

## DelayedWorkQueue

当`Worker`对象被创建后，就会不断的从工作队列中取出任务执行，下面我们看下工作队列`DelayedWorkQueue`的实现。

`DelayedWorkQueue`是`ScheduledThreadPoolExecutor`的一个静态内部类，其结构如下。

![](resources/scheduled-thread-pool-executor-2.png)

## 参考

1. [《并发编程 —— ScheduledThreadPoolExecutor》](https://juejin.im/post/5ae75604f265da0ba56753cd)
