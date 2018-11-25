package playground.algorithm;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {

    public static final int CACHE_MISS = -1;
    private static final LRUCacheListNode<Integer, Integer> CACHE_MISS_NODE = new LRUCacheListNode<>(null, null,
        CACHE_MISS, null);
    private final Map<Integer, LRUCacheListNode<Integer, Integer>> map;
    private final LRUCacheLinkedList<Integer, Integer> list;
    private final int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>(capacity);
        this.list = new LRUCacheLinkedList<>();
    }

    public int get(int key) {
        LRUCacheListNode<Integer, Integer> node = map.getOrDefault(key, CACHE_MISS_NODE);
        if (node != CACHE_MISS_NODE) {
            list.remove(node);
            map.put(key, list.addFirst(key, node.value));
        }
        return node.value;
    }

    public void put(int key, int value) {
        LRUCacheListNode<Integer, Integer> node = map.getOrDefault(key, CACHE_MISS_NODE);
        if (node != CACHE_MISS_NODE) {
            list.remove(node);
        }
        map.put(key, list.addFirst(key, value));
        if (capacity < list.size()) {
            map.remove(list.removeLast().key);
        }
    }
}

class LRUCacheLinkedList<K, V> {

    private LRUCacheListNode<K, V> head;
    private LRUCacheListNode<K, V> tail;
    private int size = 0;

    public int size() {
        return size;
    }

    public LRUCacheListNode<K, V> addFirst(K key, V value) {
        LRUCacheListNode<K, V> node = new LRUCacheListNode<>(null, key, value, head);
        if (head != null) {
            head.prev = node;
        }
        head = node;
        if (tail == null) {
            tail = head;
        }
        size++;
        return head;
    }

    public LRUCacheListNode<K, V> removeLast() {
        return remove(tail);
    }

    public LRUCacheListNode<K, V> remove(LRUCacheListNode<K, V> node) {
        if (node == null) {
            return null;
        }

        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }

        node.prev = null;
        node.next = null;
        size--;
        return node;
    }
}

class LRUCacheListNode<K, V> {

    public K key;
    public V value;
    public LRUCacheListNode<K, V> prev;
    public LRUCacheListNode<K, V> next;

    public LRUCacheListNode(LRUCacheListNode<K, V> prev, K key, V value, LRUCacheListNode<K, V> next) {
        this.prev = prev;
        this.key = key;
        this.value = value;
        this.next = next;
    }
}
