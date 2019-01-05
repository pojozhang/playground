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

### LPOP

### BLPOP

### RPUSH

### RPOP

### BRPOP

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
