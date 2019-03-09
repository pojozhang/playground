# 内存泄漏

内存泄漏是程序已经不会使用某些对象，但因为引用还存在，导致这些不再被使用的对象无法被回收。

比如以下代码中，我们不断往`list`中增加元素，只要我们没有把`list`设置为`null`，那么列表中的元素就永远不会被回收。

```java
static List<Object> list = new ArrayList<>();

for (int i = 0; i < 100; i++) {
    list.add(new Object());
}
```

可以通过`jmap`工具查看是否生成了大量同一类型的对象来判断是否发生了内存泄漏。
