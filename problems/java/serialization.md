# 序列化

序列化可以将对象保存到文件或内存中，并且在需要的时候从文件或内存中读出数据重建对象。

注意事项：

1. 对象必须实现`Serializable`接口。
2. 被`transient`修饰的字段不会被序列化。

可以在这里查看[示例代码](https://github.com/pojozhang/playground/blob/master/solutions/java/src/test/java/playground/interview/SerializationTest.java)。