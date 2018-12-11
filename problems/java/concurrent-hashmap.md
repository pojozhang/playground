# ConcurrentHashMap

![版本](https://img.shields.io/badge/java-10-red.svg)

`ConcurrentHashMap`具有`HashMap`同样的功能，但它是线程安全的，其层次结构如下。

![](resources/concurrent_hashmap_1.png)


![](resources/concurrent_hashmap_2.png)

下面我们从构造函数看起。

## ConcurrentHashMap()

```java
public ConcurrentHashMap() {
}
```

## Nullable

对于`ConcurrentHashMap`中的项，键和值**都不能**是`null`。

```java
public static void main(String[] args) {
    Map<String, String> map = new ConcurrentHashMap<>();
    // 以下3中用法都是不允许的，会抛出java.lang.NullPointerException异常。
    map.put(null, "hello");
    map.put("hello", null);
    System.out.println(map.get(null));
}
```

## 参考

1. [《How does segmentation works in ConcurrentHashMap》](https://www.quora.com/How-does-segmentation-works-in-ConcurrentHashMap)
