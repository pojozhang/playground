# 可靠传输

可靠传输关注的是在传输过程中消息是否会丢失的问题，RabbitMQ分别通过发送确认、消息持久化、消费确认来解决在不同阶段可能出现的消息丢失问题。本文主要介绍发送确认和消费确认的内容，可以在[这里](https://github.com/pojozhang/playground/blob/master/solutions/java/src/test/java/playground/rabbitmq/ConfirmationTest.java)查看相关代码。

![](resources/reliable_transmission_1.png)

## 发送确认

如果开启了Confirm模式，那么当生产者向RabbitMQ投递一条消息后，RabbitMQ会在消息投递到相应的队列后给予一个`ack`消息作为反馈；如果消息是持久化的，那么当消息被持久化到磁盘后会返回给生产者`ack`消息作为确认；如果消息的Routing Key找不到任何队列与之匹配，也会返回一个`ack`消息；如果RabbitMQ内部出现了异常，比如无法持久化消息，那么就会返回`nack`消息，在这种情况下生产者可以进行重新投递。

## 消费确认

当RabbitMQ把消息投递给一个消费者后，消费者需要对接收到的消息给予一个反馈，告诉RabbitMQ该消息是否正确处理，如果没有正确处理是否需要重新放回队列或者直接丢弃。一旦消费者确认了消息已被正确处理或者无法处理直接丢弃，那么RabbitMQ就会把该消息从队列中移除。

确认消息有以下3种。

- **Ack**

表示消息已被正确处理。

- **Reject**

表示消息处理失败。

- **Nack**

和Reject类似，表示消息处理失败，区别是Nack支持对消息进行批量确认。当确认消息是Reject或Nack时可以选择是否把消息重新放会队列中。

### 确认模式

RabbitMQ支持自动确认和手动确认两种模式。在自动确认模式下，当把消息投递给消费者后就会自动进行确认，把投递出的消息从队列中移除，如果消费者出现了异常，比如宕机等情况，那么就存在消息丢失的可能，因此通常选择手动确认模式。手动模式是指在消费者端手动编写确认的代码逻辑，在避免消息丢失的同时还会起到限流的作用。

## 参考

1. [《Consumer Acknowledgements and Publisher Confirms》](https://www.rabbitmq.com/confirms.html)
2. [《如何保证消息的可靠性传输？》](https://github.com/doocs/advanced-java/blob/master/docs/high-concurrency/how-to-ensure-the-reliable-transmission-of-messages.md)
