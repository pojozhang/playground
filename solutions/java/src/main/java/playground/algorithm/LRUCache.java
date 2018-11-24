package playground.algorithm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LRUCache {

    public static final int CACHE_MISS = -1;

    private Map<Integer, Integer> elements;

    public LRUCache(int capacity) {
        elements = new ConcurrentHashMap<>();
    }

    public int get(int key) {
        return elements.getOrDefault(key, CACHE_MISS);
    }

    public void put(int key, int value) {
        elements.put(key, value);
    }
}
