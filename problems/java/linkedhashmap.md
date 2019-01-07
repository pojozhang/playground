# LinkedHashMap

## LRU缓存

我们可以利用`LinkedHashMap`实现一个LRU缓存。

```java
public class LRUCache<K, V> extends LinkedHashMap<K, V> {

    private final int capacity;

    public LRUCache(int capacity) {
        super((int) Math.ceil(capacity / 0.75) + 1, 0.75f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return this.size() > this.capacity;
    }
}
```
