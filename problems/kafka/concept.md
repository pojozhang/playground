# 概念

## Topic

Topic是消息的集合，一个Topic可以划分成多个分区，同一个分区内的消息是有序的，不同的分区不能保证消息的顺序。

![Topic](resources/concept/concept-1.png)

## Consumer Group

多个Consumer可以组成一个Consumer Group，一个Consumer Group保证其订阅的Topic的每个分区只会被分配给组内其中一个Consumer处理，通过该机制就可以实现一个消息只会被一个Consumer处理的效果，如果一个消息要让所有的Consumer都收到，那么需要把他们放到不同的Consumer Group中。
