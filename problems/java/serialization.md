# 序列化

序列化可以将对象保存到文件或内存中，并且在需要的时候从文件或内存中读出数据重建对象。

注意事项：

1. 对象必须实现`Serializable`接口。
2. 被`transient`修饰的字段不会被序列化。

可以在这里查看[示例代码](https://github.com/pojozhang/playground/blob/master/solutions/java/src/test/java/playground/interview/SerializationTest.java)。

## serialVersionUID

通常我们会在需要被序列化的对象中显式的声明`serialVersionUID`用来标识类的版本号，只有当版本号想同时类才能被反序列化，否则会抛出异常。如果我们没有显式声明，Java会自动生成一个`serialVersionUID`，一旦类的结构发生变话，该版本号也有可能随之发生改变，导致无法反序列化。
