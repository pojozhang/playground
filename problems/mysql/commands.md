# 常用命令

## 系统变量

```sql
-- 查询变量
show (session|global) variables like 'variable_name';

-- 设置变量
set (session|global) variable_name = 'value'
```

一些常用的变量：

- max_connections: 最大连接数。
- default_storage_engine: 默认存储引擎，如InnoDB。

## 系统状态

```sql
-- 查询状态
show [session|global] status like 'variable_name';
```

一些常用的状态：

- Threads_connected: 当前建立的连接数。
