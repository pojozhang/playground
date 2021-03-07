# 生产者

Producer的整体架构如下图所示：

![Producer](resources/producer/producer-1.jpg)

Producer在发送消息时并不会直接将请求发到服务器，而是把一个或多个消息组成ProducerBatch对象缓存在消息收集器RecordAccumulator中，默认最多可以缓存32MB（可以通过buffer.memory配置修改），如果消息生产的速度过快导致缓存空间不足，在调用KafkaProducer的send()方法时会被阻塞并最多等待60s（可以通过max.block.ms配置修改），等待超时后会抛出异常。

Producer采用java.io.ByteBuffer实现消息内存的创建和释放，并实现了一个BufferPool以复用ByteBuffer，但默认只会针对16KB大小的ByteBuffer进行缓存（可以通过batch.size配置修改），其他大小的ByteBuffer不会被缓存。该参数和ProducerBatch对象的大小也有关系，在创建ProducerBatch对象时其大小不会小于batch.size的值。

Sender线程负责发送缓存在RecordAccumulator中的消息，并将那些已经发送出去但还没有收到响应的请求保存在InFlightRequests中。

Producer在投递消息时首先要决定把消息投递到Topic的哪一个分区中。如果用户指定了分区那就投递到指定的分区中；如果消息的key不为空，则对key值进行哈希（murmur2算法）后与分区数量进行取模来确定目标分区；如果用户没有指定分区并且消息的key为空，那么通过一个计数器与分区数量进行取模来确定目标分区，计数器会不断自增。

## 生产者参数

1. acks

用来指定分区中必须有多少个副本收到消息才会认为该消息写入成功。

- acks = 1（默认），只要leader副本写入消息就认为写入成功。如果leader副本在写入消息后崩溃，并且此时还没有follower副本同步该消息，此时消息丢失。
- acks = 0，发送消息后不需要等待服务器的响应。优点是吞吐量高。
- acks = -1，需要等ISR中的所有副本都写入消息后才会收到服务器的成功响应。注意，ISR中可能仅包含leader副本本身，在这种情况下等同于acks = 1，依然可能发生消息丢失的情况。
- acks = all，同acks = -1。

2. max.in.flight.requests.per.connection

指定每个连接最多缓存的请求数，也就是InFlightRequests中每个broker对应的请求数。如果需要保证消息的顺序，则建议把该值设为1。

3. retries

指定重试次数，另外可以通过retry.backoff.ms指定两次重试的间隔。

4. connections.max.idle.ms

指定在多久后关闭闲置的连接，默认是9分钟。

5. linger.ms

消息延迟发送的时间，默认是0，即立刻发送。如果该参数不为0，则在发送前会等待更多的消息加入到ProducerBatch中，从而提高吞吐量。

## Exactly-Once

## 参考

1. 《深入理解Kafka：核心设计与实践原理》
