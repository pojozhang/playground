# 消息确认机制

当RabbitMQ把消息投递给一个消费者后，消费者需要对接收到的消息给予一个反馈，告诉RabbitMQ该消息是否正确处理，如果没有正确处理是否需要重新放回队列或者直接丢弃。一旦消费者确认了消息已被正确处理或者无法处理直接丢弃，那么RabbitMQ就会把该消息从队列中移除。

确认消息有以下3种。

- **Ack**

表示消息已被正确处理。

- **Reject**

表示消息处理失败。

- **Nack**

和Reject类似，表示消息处理失败，区别是Nack支持对消息进行批量确认。当确认消息是Reject或Nack时可以选择是否把消息重新放会队列中。

## 确认模式

RabbitMQ支持自动确认和手动确认两种模式。在自动确认模式下，当把消息投递给消费者后就会自动进行确认，把投递出的消息从队列中移除，如果消费者出现了异常，比如宕机等情况，那么就存在消息丢失的可能，因此通常选择手动确认模式。手动模式是指在消费者端手动编写确认的代码逻辑，在避免消息丢失的同时还会起到限流的作用。

## 发送确认

> 可以在[这里](https://github.com/pojozhang/playground/blob/master/solutions/java/src/test/java/playground/rabbitmq/ConfirmationTest.java)查看示例代码。

## 参考

1. [《Consumer Acknowledgements and Publisher Confirms》](https://www.rabbitmq.com/confirms.html)
