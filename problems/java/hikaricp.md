# HikariCP

![版本](https://img.shields.io/badge/version-3.3.1-blue.svg?link=https://github.com/brettwooldridge/HikariCP/tree/HikariCP-3.3.1)

## getConnection()

数据库连接池的核心就是`getConnection()`方法，其位于`HikariDataSource`类中。

```java
// com.zaxxer.hikari.HikariDataSource#getConnection()
public Connection getConnection() throws SQLException
{
    if (isClosed()) {
        throw new SQLException("HikariDataSource " + this + " has been closed.");
    }

    if (fastPathPool != null) {
        return fastPathPool.getConnection();
    }

    HikariPool result = pool;
    if (result == null) {
        synchronized (this) {
            result = pool;
            if (result == null) {
            // 生成连接池的名称并校验连接池参数。
            validate();
            LOGGER.info("{} - Starting...", getPoolName());
            try {
                // 创建HikariPool对象。
                pool = result = new HikariPool(this);
                // 更新sealed字段，表示配置完成，设置后就不能再对配置进行修改。
                this.seal();
            }
            catch (PoolInitializationException pie) {
                if (pie.getCause() instanceof SQLException) {
                    throw (SQLException) pie.getCause();
                }
                else {
                    throw pie;
                }
            }
            LOGGER.info("{} - Start completed.", getPoolName());
            }
        }
    }

    // 获取连接。
    return result.getConnection();
}
```

`HikariDataSource`类中的`getConnection()`方法最后会把请求转发给`HikariPool`类处理，下面是`HikariPool`对象的构造方法。

```java
public HikariPool(final HikariConfig config)
{
    // 在基类中设置一些基本参数，如isAutoCommit、connectionTimeout等，并载入数据库驱动。
    super(config);

    this.connectionBag = new ConcurrentBag<>(this);
    this.suspendResumeLock = config.isAllowPoolSuspension() ? new SuspendResumeLock() : SuspendResumeLock.FAUX_LOCK;

    // 初始化HouseKeeper定时任务线程池。
    this.houseKeepingExecutorService = initializeHouseKeepingExecutorService();

    // 检查连接的状态。
    checkFailFast();

    // 性能监控。
    if (config.getMetricsTrackerFactory() != null) {
        setMetricsTrackerFactory(config.getMetricsTrackerFactory());
    }
    else {
        setMetricRegistry(config.getMetricRegistry());
    }

    // 健康检查。
    setHealthCheckRegistry(config.getHealthCheckRegistry());

    // 注册MBeans。
    handleMBeans(this, true);

    ThreadFactory threadFactory = config.getThreadFactory();

    // 阻塞队列，容量是最大连接数。
    LinkedBlockingQueue<Runnable> addQueue = new LinkedBlockingQueue<>(config.getMaximumPoolSize());
    this.addConnectionQueue = unmodifiableCollection(addQueue);
    // 初始化用于创建连接的线程池。
    this.addConnectionExecutor = createThreadPoolExecutor(addQueue, poolName + " connection adder", threadFactory, new ThreadPoolExecutor.DiscardPolicy());
    // 初始化用于关闭连接的线程池。
    this.closeConnectionExecutor = createThreadPoolExecutor(config.getMaximumPoolSize(), poolName + " connection closer", threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
    // 连接泄漏检测。
    this.leakTaskFactory = new ProxyLeakTaskFactory(config.getLeakDetectionThreshold(), houseKeepingExecutorService);

    // HouseKeeper线程用于维护线程池的最小连接数。
    this.houseKeeperTask = houseKeepingExecutorService.scheduleWithFixedDelay(new HouseKeeper(), 100L, housekeepingPeriodMs, MILLISECONDS);

    // 当com.zaxxer.hikari.blockUntilFilled配置项开启时，线程会阻塞直到当前连接数已经达到最小空闲连接数。
    if (Boolean.getBoolean("com.zaxxer.hikari.blockUntilFilled") && config.getInitializationFailTimeout() > 1) {
        addConnectionExecutor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        addConnectionExecutor.setMaximumPoolSize(Runtime.getRuntime().availableProcessors());

        final long startTime = currentTime();
        while (elapsedMillis(startTime) < config.getInitializationFailTimeout() && getTotalConnections() < config.getMinimumIdle()) {
        quietlySleep(MILLISECONDS.toMillis(100));
        }

        addConnectionExecutor.setCorePoolSize(1);
        addConnectionExecutor.setMaximumPoolSize(1);
    }
}
```

其中，`HouseKeeper`线程用于维护线程池的最小连接数，它实现了`Runnable`接口。

```java
// com.zaxxer.hikari.pool.HikariPool.HouseKeeper
private final class HouseKeeper implements Runnable
{
    private volatile long previous = plusMillis(currentTime(), -housekeepingPeriodMs);

    @Override
    public void run()
    {
        try {
            connectionTimeout = config.getConnectionTimeout();
            validationTimeout = config.getValidationTimeout();
            leakTaskFactory.updateLeakDetectionThreshold(config.getLeakDetectionThreshold());
            catalog = (config.getCatalog() != null && !config.getCatalog().equals(catalog)) ? config.getCatalog() : catalog;

            final long idleTimeout = config.getIdleTimeout();
            final long now = currentTime();

            // 如果时钟发生了回拨，则把所有的连接标记为已清除。
            if (plusMillis(now, 128) < plusMillis(previous, housekeepingPeriodMs)) {
                logger.warn("{} - Retrograde clock change detected (housekeeper delta={}), soft-evicting connections from pool.",
                            poolName, elapsedDisplayString(previous, now));
                previous = now;
                softEvictConnections();
                return;
            }
            else if (now > plusMillis(previous, (3 * housekeepingPeriodMs) / 2)) {
                logger.warn("{} - Thread starvation or clock leap detected (housekeeper delta={}).", poolName, elapsedDisplayString(previous, now));
            }

            previous = now;

            String afterPrefix = "Pool ";
            // 如果连接空闲时间大于0，并且最小空闲连接数小于最大连接数，那么需要处理空闲连接。
            if (idleTimeout > 0L && config.getMinimumIdle() < config.getMaximumPoolSize()) {
                logPoolState("Before cleanup ");
                afterPrefix = "After cleanup  ";

                // 找到所有没有在使用的连接。
                final List<PoolEntry> notInUse = connectionBag.values(STATE_NOT_IN_USE);
                // 要删除的连接的数量等于未使用的连接数减去最小空闲连接数。
                int toRemove = notInUse.size() - config.getMinimumIdle();
                for (PoolEntry entry : notInUse) {
                    // 判断需要被删除的连接的数量，以及连接的空闲时间，如果符合条件则关闭连接。
                    // connectionBag.reserve(entry)的用处是把该项标记为不可用。
                    if (toRemove > 0 && elapsedMillis(entry.lastAccessed, now) > idleTimeout && connectionBag.reserve(entry)) {
                        closeConnection(entry, "(connection has passed idleTimeout)");
                        toRemove--;
                    }
                }
            }

            // 打印日志。
            logPoolState(afterPrefix);

            // 增加连接数量使得连接数不低于最小空闲连接数。
            fillPool();
        }
        catch (Exception e) {
            logger.error("Unexpected exception in housekeeping task", e);
        }
    }
}

// 该方法是同步的。
private synchronized void fillPool()
{
    // 计算要新增的连接的数量。
    final int connectionsToAdd = Math.min(config.getMaximumPoolSize() - getTotalConnections(), config.getMinimumIdle() - getIdleConnections())
                                - addConnectionQueue.size();
    // 增加连接。
    for (int i = 0; i < connectionsToAdd; i++) {
        addConnectionExecutor.submit((i < connectionsToAdd - 1) ? poolEntryCreator : postFillPoolEntryCreator);
    }
}
```

当`HikariPool`对象构造完成后，就可以通过它的`getConnection()`方法获取连接，该方法最后又会调用一个重载方法。

```java
// com.zaxxer.hikari.pool.HikariPool#getConnection()
public Connection getConnection() throws SQLException
{
    return getConnection(connectionTimeout);
}

public Connection getConnection(final long hardTimeout) throws SQLException
{
    // suspendResumeLock锁的功能是暂停连接池从而允许用户修改连接池的配置等
    suspendResumeLock.acquire();
    final long startTime = currentTime();

    try {
        long timeout = hardTimeout;
        do {
            // 从connectionBag获取一个连接。
            PoolEntry poolEntry = connectionBag.borrow(timeout, MILLISECONDS);
            // 如果返回值为空，那么中断循环，抛出超时的异常。
            if (poolEntry == null) {
                break;
            }

            final long now = currentTime();
            // 如果取出的连接已被标记为不可用，或者连接的上一次访问时间距离现在已经超过了阈值并且连接已断开，那么就强制关闭连接。
            if (poolEntry.isMarkedEvicted() || (elapsedMillis(poolEntry.lastAccessed, now) > aliveBypassWindowMs && !isConnectionAlive(poolEntry.connection))) {
                closeConnection(poolEntry, poolEntry.isMarkedEvicted() ? EVICTED_CONNECTION_MESSAGE : DEAD_CONNECTION_MESSAGE);
                // 更新timeout字段，进入下一个循环。
                timeout = hardTimeout - elapsedMillis(startTime);
            }
            else {
                // 记录状态
                metricsTracker.recordBorrowStats(poolEntry, startTime);
                return poolEntry.createProxyConnection(leakTaskFactory.schedule(poolEntry), now);
            }
        } while (timeout > 0L);

        metricsTracker.recordBorrowTimeoutStats(startTime);
        throw createTimeoutException(startTime);
    }
    catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new SQLException(poolName + " - Interrupted during connection acquisition", e);
    }
    finally {
        suspendResumeLock.release();
    }
}
```

## ConcurrentBag

## FastList

## 参考

1. [《HikariCP源码分析之leakDetectionThreshold及实战解决Spark/Scala连接池泄漏》](https://www.javazhiyin.com/13856.html)
2. [《聊聊hikari连接池的isAllowPoolSuspension》](https://segmentfault.com/a/1190000013062326)
