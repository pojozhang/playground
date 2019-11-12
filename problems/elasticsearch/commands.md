# 常用命令

## 分词

- 查看分词结果

```shell
POST /index/_analyze
{
    "field: "field",
    "text": "text"
}
```

## 节点

- 查看节点信息

```shell
/_cat/nodes
```

- 查看分片信息

```shell
/_cat/shards
```

- 查看集群健康状态

```shell
/_cluster/health
```
