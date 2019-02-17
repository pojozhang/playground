# 字符串

> 本文中的示例代码可以在[这里](https://github.com/pojozhang/playground/blob/master/solutions/java/src/test/java/playground/interview/StringTest.java)查看。

## 字符串的连接


## intern()

如果字符串对象的值不在运行时常量池中，就把该字符串对象放入运行时常量池中，并返回一个指向该对象的引用；否则直接返回运行时常量池中对应的字符串值。

```java
assertNotSame("string", new String("string"));
assertSame("string", new String("string").intern());
```
