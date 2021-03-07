# 概念

## Topic

Topic是消息的集合，一个Topic可以划分成多个分区，同一个分区内的消息是有序的，不同的分区不能保证消息的顺序。

![Topic](resources/concept/concept-1.png)

分区数量允许动态增加，但是不允许减少。

## Consumer Group

多个Consumer可以组成一个Consumer Group，一个Consumer Group保证其订阅的Topic的每个分区只会被分配给组内其中一个Consumer处理，通过该机制就可以实现一个消息只会被一个Consumer处理的效果，如果一个消息要让所有的Consumer都收到，那么需要把他们放到不同的Consumer Group中。

## 副本

Kafka采用一主多从的架构提升容灾能力，leader副本负责读写请求，follower副本负责从leader副本那里同步消息而不参与读写请求的处理，当leader副本发生故障时，从follower副本中重新选举新的leader副本对外提供服务。

一个分区中所有的副本称为AR(Assigned Replicas)，与leader保持一定程度同步的副本（包括leader和follower）组成ISR(In-Sync Replicas)，与leader同步滞后的副本组成OSR(Out-of-Sync Replicas)，AR=ISR+OSR，在正常情况下OSR为空。

leader副本会维护follower副本的同步状况，从而决定一个follower副本应该处在ISR集合还是OSR集合中。在默认配置下只有处在ISR集合中的follower副本才有资格被选举为新的leader。

ISR集合中的每个副本都会维护一个LEO值(Log End Offset)，相当于当前日志分区中最后一条消息的offset+1，也就是下一条待写入的消息的offset。ISR集合中最小的LEO称为HW(High Watermark，高水位)，消费者只能拉取到HW之前的消息。

![副本](resources/concept/concept-2.jpg)

## 参考

1. 《深入理解Kafka：核心设计与实践原理》
