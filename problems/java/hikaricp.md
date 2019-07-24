# HikariCP

![状态](https://img.shields.io/badge/status-processing-blue.svg) ![版本](https://img.shields.io/badge/version-3.3.1-blue.svg?link=https://github.com/brettwooldridge/HikariCP/tree/HikariCP-3.3.1)

## 入口

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

## 构造HikariPool

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

其中，`addConnectionExecutor`线程池用于创建连接，`closeConnectionExecutor`线程池用于关闭连接。

## HouseKeeper

`HouseKeeper`线程用于维护线程池的最小连接数，它实现了`Runnable`接口。

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
                    // connectionBag.reserve()的作用是把该项标记为不可用，该方法返回false表示连接正在被使用，返回true表示连接闲置。
                    if (toRemove > 0 && elapsedMillis(entry.lastAccessed, now) > idleTimeout && connectionBag.reserve(entry)) {
                        closeConnection(entry, "(connection has passed idleTimeout)");
                        toRemove--;
                    }
                }
            }

            // 打印日志。
            logPoolState(afterPrefix);

            // 增加连接数量使得空闲连接数在不超过最大连接数的情况下不低于最小空闲连接数。
            fillPool();
        }
        catch (Exception e) {
            logger.error("Unexpected exception in housekeeping task", e);
        }
    }
}
```

## 创建连接

`fillPool()`方法用来维持连接池中空闲连接的数量。

```java
// 该方法是同步的。
private synchronized void fillPool()
{
    // 计算要新增的连接的数量。
    // 连接总数不能超过最大连接数。
    final int connectionsToAdd = Math.min(config.getMaximumPoolSize() - getTotalConnections(), config.getMinimumIdle() - getIdleConnections())
                                - addConnectionQueue.size();
    // 增加连接。
    for (int i = 0; i < connectionsToAdd; i++) {
        addConnectionExecutor.submit((i < connectionsToAdd - 1) ? poolEntryCreator : postFillPoolEntryCreator);
    }
}
```

`fillPool()`方法中用到两个对象来创建连接，分别是`poolEntryCreator`和`postFillPoolEntryCreator`，它们都是`PoolEntryCreator`类的实例，仅在打印日志的参数部分有所区别。

```java
// com.zaxxer.hikari.pool.HikariPool.PoolEntryCreator
private final class PoolEntryCreator implements Callable<Boolean>
{
    private final String loggingPrefix;

    PoolEntryCreator(String loggingPrefix)
    {
        this.loggingPrefix = loggingPrefix;
    }

    @Override
    public Boolean call()
    {
        long sleepBackoff = 250L;
        // 判断连接池的状态，以及是否有必要创建新的连接。
        while (poolState == POOL_NORMAL && shouldCreateAnotherConnection()) {
            // 构建PoolEntry对象。
            // createPoolEntry()方法负责构建JDBC的Connection对象，以及执行初始化SQL等操作。
            final PoolEntry poolEntry = createPoolEntry();
            // 如果构建成功，表示成功获得连接，那么就把得到的PoolEntry对象放入connectionBag中。
            if (poolEntry != null) {
                connectionBag.add(poolEntry);
                logger.debug("{} - Added connection {}", poolName, poolEntry.connection);
                if (loggingPrefix != null) {
                    logPoolState(loggingPrefix);
                }
                return Boolean.TRUE;
            }
            // 如果获得连接失败，那么当前线程在等待一段时间后进行重试。
            quietlySleep(sleepBackoff);
            sleepBackoff = Math.min(SECONDS.toMillis(10), Math.min(connectionTimeout, (long) (sleepBackoff * 1.5)));
        }
        return Boolean.FALSE;
    }

    private synchronized boolean shouldCreateAnotherConnection() {
        // 如果当前的连接数量还没有达到最大值，并且仍有线程在等待获取连接或者空闲连接低于阈值，那么有必要创建新的连接。
        return getTotalConnections() < config.getMaximumPoolSize() &&
        (connectionBag.getWaitingThreadCount() > 0 || getIdleConnections() < config.getMinimumIdle());
    }
}

// com.zaxxer.hikari.pool.HikariPool#createPoolEntry
private PoolEntry createPoolEntry()
{
    try {
        final PoolEntry poolEntry = newPoolEntry();
        // 连接的存活时间。
        final long maxLifetime = config.getMaxLifetime();
        if (maxLifetime > 0) {
        // 设置一个随机的偏差variance，以防止大量连接同时失效。
        final long variance = maxLifetime > 10_000 ? ThreadLocalRandom.current().nextLong( maxLifetime / 40 ) : 0;
        final long lifetime = maxLifetime - variance;

        // 创建一个定时任务用来关闭连接，触发时间是连接的存活时间。
        poolEntry.setFutureEol(houseKeepingExecutorService.schedule(
            () -> {
                if (softEvictConnection(poolEntry, "(connection has passed maxLifetime)", false)) {
                    addBagItem(connectionBag.getWaitingThreadCount());
                }
            },
            lifetime, MILLISECONDS));
        }

        return poolEntry;
    }
    catch (ConnectionSetupException e) {
        if (poolState == POOL_NORMAL) {
            logger.error("{} - Error thrown while acquiring connection from data source", poolName, e.getCause());
            lastConnectionFailure.set(e);
        }
        return null;
    }
    catch (SQLException e) {
        if (poolState == POOL_NORMAL) {
            logger.debug("{} - Cannot acquire connection from data source", poolName, e);
            lastConnectionFailure.set(new ConnectionSetupException(e));
        }
        return null;
    }
    catch (Exception e) {
        if (poolState == POOL_NORMAL) {
            logger.error("{} - Error thrown while acquiring connection from data source", poolName, e);
            lastConnectionFailure.set(new ConnectionSetupException(e));
        }
        return null;
    }
}

// 使连接失效。
private boolean softEvictConnection(final PoolEntry poolEntry, final String reason, final boolean owner)
{
    // 打开失效标记。
    poolEntry.markEvicted();
    // owner变量表示是否是用户触发的，比如用户可以主动调用HikariDataSource#evictConnection(Connection)方法使连接失效，此时owner变量的值是true，如果不是用户主动触发的，该变量的值就是false。
    // connectionBag.reserve()方法用于保留还未被使用的对象，如果对象已经在被使用中，那么该方法返回false，表示保留失败，否则返回true。
    if (owner || connectionBag.reserve(poolEntry)) {
        // 如果是用户主动触发的，或者连接还未被使用的，那么关闭连接。
        closeConnection(poolEntry, reason);
        return true;
    }

    // 如果既不是用户主动触发，也不是闲置中的连接，那么在之后连接再次被取出时，由于被标记为了已失效，因此也不会被使用。
    return false;
}
```

## getConnection()

当`HikariPool`对象构造完成后，就可以通过它的`getConnection()`方法获取连接，该方法又会调用一个重载方法。

```java
// com.zaxxer.hikari.pool.HikariPool#getConnection()
public Connection getConnection() throws SQLException
{
    return getConnection(connectionTimeout);
}

public Connection getConnection(final long hardTimeout) throws SQLException
{
    // suspendResumeLock锁的功能是暂停连接池从而允许用户修改连接池的配置、模拟数据库连接故障等。
    // 当我们没有设置isAllowPoolSuspension字段时，suspendResumeLock是一个空实现，不会起到锁的作用。
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
            // 这里不直接判断连接是否存活的原因是处于性能的考虑，因为检测连接存活需要执行SQL语句，所以这里加上一个时间阈值，当连接上一次的访问时间超过了一定的范围才会进行检测。
            if (poolEntry.isMarkedEvicted() || (elapsedMillis(poolEntry.lastAccessed, now) > aliveBypassWindowMs && !isConnectionAlive(poolEntry.connection))) {
                closeConnection(poolEntry, poolEntry.isMarkedEvicted() ? EVICTED_CONNECTION_MESSAGE : DEAD_CONNECTION_MESSAGE);
                // 更新timeout字段，进入下一个循环。
                timeout = hardTimeout - elapsedMillis(startTime);
            }
            else {
                // 记录状态
                metricsTracker.recordBorrowStats(poolEntry, startTime);
                // 创建Connection的代理类。
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

## 动态创建类

当我们基于源代码调试时，会发现程序运行到`poolEntry.createProxyConnection()`这一步就会报错，进入该方法查看后，发现方法体没有实现任何功能，仅仅是抛出了异常。

```java
static ProxyConnection getProxyConnection(final PoolEntry poolEntry, final Connection connection, final FastList<Statement> openStatements, final ProxyLeakTask leakTask, final long now, final boolean isReadOnly, final boolean isAutoCommit){
    // Body is replaced (injected) by JavassistProxyFactory
    throw new IllegalStateException("You need to run the CLI build and you need target/classes in your classpath to run.");
}
```

那么真正的实现去哪了呢？实际上看了作者的注释就能明白，真正的方法体会由Javassist动态生成，为了得到一个可正常运行的版本，我们需要用Maven进行构建。

```shell
mvn test package
```

上述命令执行完成后会发现在`target/classes/com/zaxxer/hikari/pool`目录下生成了几个以HikariProxy开头的类文件，分别是：

1. HikariProxyCallableStatement.class
2. HikariProxyConnection.class
3. HikariProxyPreparedStatement.class
4. HikariProxyResultSet.class
5. HikariProxyStatement.class


## ConcurrentBag

## FastList

## 参考

1. [《HikariCP源码分析之leakDetectionThreshold及实战解决Spark/Scala连接池泄漏》](https://www.javazhiyin.com/13856.html)
2. [《聊聊hikari连接池的isAllowPoolSuspension》](https://segmentfault.com/a/1190000013062326)
