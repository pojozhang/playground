# Spring JPA

## UTF8MB4编码

当我们需要把含有emoji表情的数据插入到MySQL数据库中时，需要把编码设置成utf8mb4，以hikari数据源为例，正确的配置如下。

```yml
spring:
  datasource:
    url: jdbc:mysql://host:port/database?useUnicode=true&characterEncoding=UTF-8
    username: username
    password: password
    hikari:
      connection-init-sql: SET NAMES utf8mb4
```