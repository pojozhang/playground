# HikariCP

![版本](https://img.shields.io/badge/version-3.3.1-blue.svg?link=https://github.com/brettwooldridge/HikariCP/tree/HikariCP-3.3.1)

## getConnection()

数据库连接池的核心就是`getConnection()`方法，位于`HikariDataSource`类中。

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

`HikariDataSource`类中的`getConnection()`方法仅仅是对`HikariPool`类的代理。

```java
public HikariPool(final HikariConfig config)
{
    // 在基类中设置一些基本参数，如isAutoCommit、connectionTimeout等，并载入数据库驱动。
    super(config);

    this.connectionBag = new ConcurrentBag<>(this);
    this.suspendResumeLock = config.isAllowPoolSuspension() ? new SuspendResumeLock() : SuspendResumeLock.FAUX_LOCK;

    // 定时任务。
    this.houseKeepingExecutorService = initializeHouseKeepingExecutorService();

    // 检查连接的状态。
    checkFailFast();

    if (config.getMetricsTrackerFactory() != null) {
        setMetricsTrackerFactory(config.getMetricsTrackerFactory());
    }
    else {
        setMetricRegistry(config.getMetricRegistry());
    }

    setHealthCheckRegistry(config.getHealthCheckRegistry());

    handleMBeans(this, true);

    ThreadFactory threadFactory = config.getThreadFactory();

    LinkedBlockingQueue<Runnable> addQueue = new LinkedBlockingQueue<>(config.getMaximumPoolSize());
    this.addConnectionQueue = unmodifiableCollection(addQueue);
    this.addConnectionExecutor = createThreadPoolExecutor(addQueue, poolName + " connection adder", threadFactory, new ThreadPoolExecutor.DiscardPolicy());
    this.closeConnectionExecutor = createThreadPoolExecutor(config.getMaximumPoolSize(), poolName + " connection closer", threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());

    this.leakTaskFactory = new ProxyLeakTaskFactory(config.getLeakDetectionThreshold(), houseKeepingExecutorService);

    // HouseKeeper线程用于维护线程池的最小连接数。
    this.houseKeeperTask = houseKeepingExecutorService.scheduleWithFixedDelay(new HouseKeeper(), 100L, housekeepingPeriodMs, MILLISECONDS);

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

## ConcurrentBag

## FastList
