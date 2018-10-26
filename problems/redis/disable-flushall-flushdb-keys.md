# 禁用FLUSHALL/FLUSHDB/KEYS命令

处于安全考虑，在生产环境中我们应该禁用`FLUSHALL`、`FLUSHDB`以及`KEYS`命令，以防止数据被清空，以及键过多导致Redis阻塞。解决方法是在Redis的配置文件中增加以下配置。

```bash
rename-command FLUSHALL ""
rename-command FLUSHDB ""
rename-command KEYS ""
```