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

## 优先副本的选举

在理想情况下，每个broker上的leader副本数量应该是相同的，从而让每个节点的负载尽可能相同，但随着时间的推移，总有节点会发生故障导致旧的leader副本下线，并选举出一个新的leader，这样就会导致节点的负载失去平衡。

Kafka引入了优先副本的概念，优先副本实际上就是AR集合中的第一个副本。

在创建主题的时候，主题的分区及副本会尽可能的分布到各个broker上，比如创建一个有3个分区的主题，每个分区有3个副本，如果有3个broker，那么每个broker上会存在一个leader副本。

```bash
Topic: test	Partition: 0	Leader: 2	Replicas: 2,3,1	Isr: 2,3,1
Topic: test	Partition: 1	Leader: 3	Replicas: 3,1,2	Isr: 3,1,2
Topic: test	Partition: 2	Leader: 1	Replicas: 1,2,3	Isr: 1,2,3
```

理想情况下优先副本就是分区的leader副本，优先副本的选举就是指通过一种方式将优先副本选举为leader副本（也可以称为分区平衡）。

Kafka提供了自动平衡的功能（需设置auto.leader.rebalance.enable参数，默认开启），控制器会用一个定时任务轮询所有的broker节点并统计它们的分区不平衡率（非优先副本的leader个数/分区总数），如果超过阈值则会启动优先副本的选举。在生产中不建议开启自动分区功能，如有需要可以通过Kafka提供的脚本工具手动执行。

## 分区leader选举

分区leader的选举也由控制器负责实施。

当创建分区时或分区原来的leader副本下线时都会执行leader的选举过程，此时的选举策略是按顺序查找AR集合中第一个存活的副本，并且该副本也在ISR集合中。之所以按照AR集合中副本的顺序是因为分区的AR集合在分配的时候就固定，除非发生重分配（需要通过Kafka提供的脚本手动重分配），而ISR集合中副本的顺序可能改变（比如副本宕机就会改变ISR）。如果ISR集合中没有可用的副本，就需要查看unclean.leader.election.enable参数是否允许从非ISR集合中选举leader，如果允许则从AR集合中找到第一个存活的副本作为leader。

当对分区进行重分配时也会进行leader的选举过程，重分配是只通过Kafka提供的脚本工具把分区迁移到其他节点上。此时的选举策略是从重分配的AR集合中找到第一个存活的副本且该副本在当前的ISR集合中。

当发生优先副本选举时，此时的策略是把优先副本设置成leader，即AR集合中的第一个副本。

当节点被优雅关闭时，节点上的leader副本都会下线，相应的分区也会触发leader选举，此时的策略是从AR集合中找到第一个存活的副本且位于ISR集合中，并且这个副本不处在正在关闭的节点上。

## 参考

1. 《深入理解Kafka：核心设计与实践原理》
