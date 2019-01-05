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

## 哈希表

### HSET

### HGET

### HEXISTS

### HDEL

### HINCRBY

### HSCAN

## 无序集合

### SADD

### SCARD

### SISMEMBER

### SMEMBERS

### SDIFF

### SINTER

### SUNION

### SSCAN

## 有序集合

## 参考

1. [《Redis命令参考》](http://redisdoc.com/index.html)
