# HikariCP

![版本](https://img.shields.io/badge/version-3.3.1-blue.svg?link=https://github.com/brettwooldridge/HikariCP/tree/HikariCP-3.3.1)

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
// com.zaxxer.hikari.pool.PoolEntry#createProxyConnection
Connection createProxyConnection(final ProxyLeakTask leakTask, final long now){
    return ProxyFactory.getProxyConnection(this, connection, openStatements, leakTask, now, isReadOnly, isAutoCommit);
}

// com.zaxxer.hikari.pool.ProxyFactory#getProxyConnection
static ProxyConnection getProxyConnection(final PoolEntry poolEntry, final Connection connection, final FastList<Statement> openStatements, final ProxyLeakTask leakTask, final long now, final boolean isReadOnly, final boolean isAutoCommit){
    // Body is replaced (injected) by JavassistProxyFactory
    throw new IllegalStateException("You need to run the CLI build and you need target/classes in your classpath to run.");
}
```

那么真正的实现去哪了呢？实际上看了作者的注释就能明白，真正的方法体会由Javassist动态生成。为了得到一个可正常运行的版本，我们需要用Maven进行构建。

```shell
mvn test package
```

上述命令会调用`com.zaxxer.hikari.util.JavassistProxyFactory`类动态生成类文件，命令执行完成后会在`target/classes/com/zaxxer/hikari/pool`路径下生成几个以HikariProxy开头的类文件，分别是：

1. HikariProxyCallableStatement.class
2. HikariProxyConnection.class
3. HikariProxyPreparedStatement.class
4. HikariProxyResultSet.class
5. HikariProxyStatement.class

此时再去看编译后的`getProxyConnection()`方法，可以看到原来抛出异常的代码已经被替换掉了。

```java
static ProxyConnection getProxyConnection(PoolEntry var0, Connection var1, FastList<Statement> var2, ProxyLeakTask var3, long var4, boolean var6, boolean var7) {
    return new HikariProxyConnection(var0, var1, var2, var3, var4, var6, var7);
}
```

那么作者为什么要动态地创建类呢？如果我们看一下这些生成的类中的方法实现，就会发现其中大部分都是在基类方法的外面包了一层异常捕获，比如下面这段代码。

```java
public Statement createStatement() throws SQLException {
    try {
        return super.createStatement();
    } catch (SQLException var2) {
        throw this.checkException(var2);
    }
}
```

作者认为手动编写这些代码比较麻烦，因为有的类中需要重写几十个方法，因此采用了动态创建类的方法，可以在[这里](https://github.com/brettwooldridge/HikariCP/issues/1198)查看作者的回复。

## ConcurrentBag

ConcurrentBag是一个线程安全的集合类，下面先从`add()`方法看起。

### add(T)

把资源添加到集合中。

```java
public void add(final T bagEntry)
{
    // 检查集合状态。
    if (closed) {
        LOGGER.info("ConcurrentBag has been closed, ignoring add()");
        throw new IllegalStateException("ConcurrentBag has been closed, ignoring add()");
    }

    // 把资源添加到一个列表中。
    // sharedList的类型是CopyOnWriteArrayList。
    sharedList.add(bagEntry);

    // 如果有线程在等待资源并且该资源还没有被使用，那么就尝试把资源插入到同步队列中（offer操作），如果offer()方法返回true，说明该资源已经被某个线程获取，否则说明当前没有线程在等待资源，那么就继续循环。
    while (waiters.get() > 0 && bagEntry.getState() == STATE_NOT_IN_USE && !handoffQueue.offer(bagEntry)) {
        // 尝试让出CPU时间。
        yield();
    }
}
```

### borrow(long, TimeUnit)

从集合中借用一个资源。

```java
public T borrow(long timeout, final TimeUnit timeUnit) throws InterruptedException
{
    //从ThreadLocal中取出资源列表，遍历列表，找到一个未被使用的资源。
    final List<Object> list = threadList.get();
    for (int i = list.size() - 1; i >= 0; i--) {
        final Object entry = list.remove(i);
        final T bagEntry = weakThreadLocals ? ((WeakReference<T>) entry).get() : (T) entry;
        if (bagEntry != null && bagEntry.compareAndSet(STATE_NOT_IN_USE, STATE_IN_USE)) {
            return bagEntry;
        }
    }

    // 如果ThreadLocal中没有可用的资源，那么进行下面的步骤。
    // 更新等待线程计数。
    final int waiting = waiters.incrementAndGet();
    try {
        // 从线程共享的列表中遍历全部资源，找到一个未被使用的资源。
        for (T bagEntry : sharedList) {
            // 用CAS把资源的状态改为正在使用中。
            if (bagEntry.compareAndSet(STATE_NOT_IN_USE, STATE_IN_USE)) {
                // 请求增加资源。
                if (waiting > 1) {
                    listener.addBagItem(waiting - 1);
                }
                return bagEntry;
            }
        }

        // 执行到这里，说明当前没有资源或者资源都在被使用，那么请求增加资源。
        listener.addBagItem(waiting);

        // 超时时间。
        timeout = timeUnit.toNanos(timeout);
        do {
            final long start = currentTime();
            // 等待add()方法被调用产生一个新资源。
            final T bagEntry = handoffQueue.poll(timeout, NANOSECONDS);
            if (bagEntry == null || bagEntry.compareAndSet(STATE_NOT_IN_USE, STATE_IN_USE)) {
                return bagEntry;
            }

            // 更新剩余超时时间。
            timeout -= elapsedNanos(start);
        } while (timeout > 10_000);

        return null;
    }
    finally {
        // 更新等待线程计数。
        waiters.decrementAndGet();
    }
}
```

### requite(T)

回收一个资源。

```java
public void requite(final T bagEntry)
{
    // 将资源的状态设置为未被使用。
    bagEntry.setState(STATE_NOT_IN_USE);

    // 如果有线程在等待资源，那么尝试把当前资源放到同步队列中。
    for (int i = 0; waiters.get() > 0; i++) {
        if (bagEntry.getState() != STATE_NOT_IN_USE || handoffQueue.offer(bagEntry)) {
            return;
        }
        else if ((i & 0xff) == 0xff) {
            parkNanos(MICROSECONDS.toNanos(10));
        }
        else {
            yield();
        }
    }

    // 把资源放到ThreadLocal的资源列表中。
    final List<Object> threadLocalList = threadList.get();
    if (threadLocalList.size() < 50) {
        threadLocalList.add(weakThreadLocals ? new WeakReference<>(bagEntry) : bagEntry);
    }
}
```

### reserve(T)

把资源状态设置为被保留的。

```java
public boolean reserve(final T bagEntry)
{
    return bagEntry.compareAndSet(STATE_NOT_IN_USE, STATE_RESERVED);
}
```

### remove(T)

从集合中删除资源。

```java
public boolean remove(final T bagEntry)
{
    // CAS修改资源状态。
    if (!bagEntry.compareAndSet(STATE_IN_USE, STATE_REMOVED) && !bagEntry.compareAndSet(STATE_RESERVED, STATE_REMOVED) && !closed) {
        LOGGER.warn("Attempt to remove an object from the bag that was not borrowed or reserved: {}", bagEntry);
        return false;
    }

    // 从列表中移除资源。
    final boolean removed = sharedList.remove(bagEntry);
    if (!removed && !closed) {
        LOGGER.warn("Attempt to remove an object from the bag that does not exist: {}", bagEntry);
    }

    return removed;
}
```

## FastList

`ConcurrentBag`类中用到了`FastList`作为每个线程ThreadLocal中的资源列表。它实现了`List`接口，但仅仅实现了一些必要的方法，它的原理和`ArrayList`类似，底层存储都是基于数组，最大的区别是删除了索引范围的检查。
对比两者的`get()`方法可以看到，`FastList`中的方法没有对索引范围进行检查，因此执行速度上会更快。

```java
// com.zaxxer.hikari.util.FastList#get
public T get(int index)
{
    return elementData[index];
}

// java.util.ArrayList#get
public E get(int index) {
    rangeCheck(index);
    return elementData(index);
}
```

## 连接的生命周期

### 连接的创建

如果设置了`initializationTimeout`配置项，在初始化`HikariPool`对象时（也就是首次获取连接时）会以同步调用的方式创建一个连接，以验证连接的可用性，如果创建失败会重试，直到连接的建立时间超过`initializationTimeout`，如果还是没有成功那么就抛异常，实现fail-fast机制。在其他情况下都由`addConnectionExecutor`线程池负责创建新的连接，这是一个只有一个线程的线程池。连接创建后会被放到`ConcurrentBag`集合中。

### 连接的获取

线程通过调用`ConcurrentBag`的`borrow()`方法获取连接。

### 连接的回收

当调用`ProxyConnection`对象的`close()`方法时，连接会被回收，重新放回`ConcurrentBag`集合中，这里实际上只是改变连接的状态，连接对象本身一直存放在`ConcurrentBag`集合中。

### 连接的关闭

和创建连接类似，有一个专门的线程池`closeConnectionExecutor`对连接进行关闭并将其从`ConcurrentBag`集合中移除。有以下几种情况会关闭连接：

1. 连接存活时间超过MaxLifetime，通过在`HikariPool#createPoolEntry`方法中注册一个定时任务来触发关闭操作。
2. 连接空闲时间超过idleTimeout，在`HikariPool.HouseKeeper`中实现。

### 连接的补充

连接被关闭后需要进行补充，以保证连接数不少于minimumIdle。通过`HikariPool#fillPool`方法实现，大致流程是计算出需要新增的连接的数量然后提交给`addConnectionExecutor`线程池执行，具体可以看上文中关于`fillPool()`方法的说明。

## 参考

1. [《HikariCP源码分析之leakDetectionThreshold及实战解决Spark/Scala连接池泄漏》](https://www.javazhiyin.com/13856.html)
2. [《聊聊hikari连接池的isAllowPoolSuspension》](https://segmentfault.com/a/1190000013062326)
3. [《Hikaricp源码解读（3）—— ConcurrentBag介绍》](https://blog.csdn.net/taisenki/article/details/78329558)
4. [《Hikaricp源码解读（5）—— 物理连接生命周期介绍》](https://blog.csdn.net/taisenki/article/details/78330324)
