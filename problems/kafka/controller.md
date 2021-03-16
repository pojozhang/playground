# 控制器

在一个Kafka集群中存在一个或多个broker，其中一个会被选举为控制器，它负责集群中所有分区和副本的状态。它负责为leader副本出现故障的分区选举新的leader；当检测到某个分区的ISR集合发生变化时，它需要通知所有broker更新元数据信息；当用kafka-topics.sh脚本为某个topic增加分区时，它负责对分区重新分配。

## 控制器的选举

每个broker在启动时会读取ZooKeeper上`/controller`节点的值，该节点包含以下三个信息：

```json
{"version":1,"brokerid":3,"timestamp":"1615781688374"}
```

- version：固定为1。
- brokerid：成为控制器的broker的编号。
- timestamp：竞选为控制器时的时间戳。

如果读取到的brokerid不为-1，则表示已经有其他broker成功竞选为控制器，那么当前broker放弃竞选；如果该节点不存在或者数据异常那么当前broker就会尝试去创建该节点，如果创建成功则表示竞选成功，在多个broker同时创建的情况下只有一个broker可以创建成功。每个broker都会监听`/controller`节点的变化以感知控制器的变更。

## 参考

1. 《深入理解Kafka：核心设计与实践原理》
