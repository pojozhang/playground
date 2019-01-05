# 命令

## 字符串

### SET

```bash
SET key value [EX seconds] [PX milliseconds] [NX|XX]
```

设置`key`的值为`value`。

- EX：过期时间，单位是秒。
- PX：过期时间，单位是毫秒。
- NX：当`key`不存在时才会执行操作。
- XX：当`key`存在时才会执行操作。

可以利用`SET key value EX seconds NX`命令作为分布式锁来使用。

### SETEX

```bash
SETEX key seconds value
```

设置`key`的值为`value`并设置过期时间为`seconds`秒。

### SETNX

```bash
SETNX key value
```

当`key`不存在时，设置`key`的值为`value`，否则不做任何操作。

### GET

```bash
GET key
```

返回`key`对应的值。

### INCR

```bash
INCR key
```

将`key`的值自增1。如果值不是整数类型那么会报错，如果值不存在那么会先被初始化为0，再进行自增。

### INCRBY

```bash
INCRBY key increment
```

将`key`的值增加`increment`。如果值不是整数类型那么会报错，如果值不存在那么会先被初始化为0，再进行自增。

## 有序列表

### LPUSH

```bash
LPUSH key value [value ...]
```

把一个或多个值添加到有序列表`key`的表头。`LPUSH key v1 v2 v3`等价于原子性地执行`LPUSH key v1`，`LPUSH key v2`，`LPUSH key v3`三条语句。如果列表`key`不存在那么会先创建一个空列表。

### LPOP

```bash
LPOP key
```

弹出列表`key`的表头元素，如果列表不存在或列表为空，则返回`nil`。

### BLPOP

```bash
BLPOP key [key ...] seconds
```

BLPOP是LPOP的阻塞版本，它可以同时监听多个有序列表，弹出第一个非空列表的表头元素，如果监听的每个列表都不存在或列表都为空，那么该命令会一直阻塞，直到有一个列表不为空或者达到了指定的超时时间。当超时时间设置为0时表示阻塞时间没有限制。

### RPUSH

```bash
RPUSH key value [value ...]
```

把一个或多个值添加到有序列表`key`的表尾。`RPUSH key v1 v2 v3`等价于原子性地执行`RPUSH key v1`，`RPUSH key v2`，`RPUSH key v3`三条语句。如果列表`key`不存在那么会先创建一个空列表。

### RPOP

```bash
RPOP key
```

弹出列表`key`的表尾元素，如果列表不存在或列表为空，则返回`nil`。

### BRPOP

```bash
BRPOP key [key ...] seconds
```

BRPOP是RPOP的阻塞版本，它可以同时监听多个有序列表，弹出第一个非空列表的表尾元素，如果监听的每个列表都不存在或列表都为空，那么该命令会一直阻塞，直到有一个列表不为空或者达到了指定的超时时间。当超时时间设置为0时表示阻塞时间没有限制。

### LRANGE

```bash
LRANGE key start stop
```

返回有序列表`key`中下标在区间[`start`, `stop`]（闭区间）中的元素。当下标是负数时表示从列表的表尾开始遍历，比如-1表示列表中最后一个元素的下标。

```bash
LPUSH test 1 2
LRANGE test -2 -1
1) "2"
2) "1"
```

### LLEN

```bash
LLEN key
```

获取有序列表`key`的元素的数量。

## 哈希表

### HSET

```bash
HSET key field value
```

设置哈希表`key`中的属性`field`的值为`value`。

### HGET

```bash
HGET key field
```

获取哈希表`key`中属性`field`的值。

### HEXISTS

```bash
HEXISTS key field
```

判断哈希表`key`中是否存在属性`field`。

### HDEL

```bash
HDEL key field [field ...]
```

删除哈希表`key`中一个或多个属性。

### HINCRBY

```bash
HINCRBY key field increment
```

把哈希表`key`中的属性`field`的值增加`increment`，如果哈希表或属性不存在，会先创建哈希表或创建属性，并将属性的值初始化为0。

## 集合

### SADD

```bash
SADD key member [member ...]
```

往集合`key`中增加一个或多个元素。

### SCARD

```bash
SCARD key
```

获取集合`key`中元素的数量。

### SISMEMBER

```bash
SISMEMBER key member
```

判断集合`key`是否包含元素`member`。

### SMEMBERS

```bash
SMEMBERS key
```

获取集合`key`中所有的元素。

### SDIFF

```bash
SDIFF key [key ...]
```

返回多个集合的差集。

### SINTER

```bash
SINTER key [key ...]
```

返回多个集合的交集。

### SUNION

```bash
SUNION key [key ...]
```

返回多个集合的并集。

## 有序集合

### ZDD

```bash
ZADD key score member [[score member] [score member] ...]
```

把一个或多个元素`member`及其分数`score`放到有序集合`key`中，当元素已经存在时会更新它的分数。

### ZCARD

```bash
ZCARD key
```

获取有序集合`key`中元素的数量。

### ZCOUNT

```bash
ZCOUNT key min max
```

获取有序集合`key`中分数在[`min`, `max`]区间内的元素的数量。

### ZRANGE

```bash
ZRANGE key start stop [WITHSCORES]
```

获取有序集合`key`中元素下标在[`start`, `stop`]区间内的元素，按元素的分数从小到大进行排序，分数相同的按字典序排序。如果使用了`WITHSCORES`参数，那么返回结果中会包含元素的分数。下标也可以是负数，比如-1表示有序集合中最后一个元素的下标。

```bash
ZADD age 25 Ben 30 Jack

ZRANGE age 0 -1
1) "Ben"
2) "Jack"

ZRANGE age 0 -1 WITHSCORES
1) "Ben"
2) "25"
3) "Jack"
4) "30"
```

### ZREVRANGE

```bash
ZREVRANGE key start stop [WITHSCORES]
```

`ZREVRANGE`和`ZRANGE`功能类似，仅仅是排序规则相反，按元素分数从大到小的顺序进行排序。

### ZRANGEBYSCORE

```bash
ZRANGEBYSCORE key min max [WITHSCORES] [LIMIT offset count]
```

返回有序集合`key`中分数在[`min`, `max`]区间内的元素。如果加上`WITHSCORES`参数返回结果中会带上元素的分数。参数`LIMIT`部分可以用作分页查询。

```bash
ZADD age 25 Ben 30 Jack

ZRANGEBYSCORE age 1 30 WITHSCORES LIMIT 0 1
1) "Ben"
2) "25"
```

参数`min`和`max`可以设为特殊的`-inf`和`+inf`分别表示负无穷和正无穷。

```bash
ZRANGEBYSCORE age -inf +inf WITHSCORES LIMIT 0 1
1) "Ben"
2) "25"
```

在分数前加上`(`符号还可以使查询的区间从默认的闭区间变成开区间。

```bash
ZRANGEBYSCORE age 1 30 WITHSCORES
1) "Ben"
2) "25"
3) "Jack"
4) "30"

ZRANGEBYSCORE age 1 (30 WITHSCORES
1) "Ben"
2) "25"
```

### ZREVRANGEBYSCORE

```bash
ZREVRANGEBYSCORE key max min [WITHSCORES] [LIMIT offset count]
```

和`ZRANGEBYSCORE`功能相同，区别是返回结果按元素的分数从大到小进行排序。

### ZRANK

```bash
ZRANK key member
```

返回有序集合`key`中元素`member`的排名，排名按元素分数从小到大进行计算，最小的元素的排名是0。如果有序集合不包含元素`member`，那么返回`nil`。

### ZREVRANK

```bash
ZREVRANK key member
```

和`ZRANK`一样也是返回有序集合`key`中元素`member`的排名，区别是排名按元素分数从大到小进行计算，最大的元素的排名是0。

### ZSCORE

```bash
ZSCORE key member
```

返回有序集合`key`中元素`member`的分数，如果不存在该元素那么返回`nil`。

### ZREM

```bash
ZREM key member [member ...]
```

移除有序集合`key`中的一个或多个元素。

### ZREMRANGEBYRANK

```bash
ZREMRANGEBYRANK key start stop
```

移除有序集合`key`中排名在[start, stop]区间内的元素，当下标是负数时表示从列表的表尾开始遍历，比如-1表示列表中最后一个元素的下标。

### ZREMRANGEBYSCORE

```bash
ZREMRANGEBYSCORE key min max
```

移除有序集合`key`中分数在[min, max]区间内的元素。

## 参考

1. [《Redis命令参考》](http://redisdoc.com/index.html)
