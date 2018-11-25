package playground.algorithm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class LRUCacheTest {

    private LRUCache cache;

    @Test
    void case_1() {
        cache = new LRUCache(2);

        assertEquals(LRUCache.CACHE_MISS, cache.get(1));
    }

    @Test
    void case_2() {
        cache = new LRUCache(2);

        cache.put(1, 1);

        assertEquals(1, cache.get(1));
    }

    @Test
    void case_3() {
        cache = new LRUCache(2);
        cache.put(1, 1);
        cache.put(2, 2);

        assertEquals(1, cache.get(1));

        cache.put(3, 3);

        assertEquals(LRUCache.CACHE_MISS, cache.get(2));

        cache.put(4, 4);

        assertEquals(LRUCache.CACHE_MISS, cache.get(1));
        assertEquals(3, cache.get(3));
        assertEquals(4, cache.get(4));
    }

    @Test
    void case_4() {
        cache = new LRUCache(1);
        cache.put(2, 1);

        assertEquals(1, cache.get(2));

        cache.put(3, 2);

        assertEquals(LRUCache.CACHE_MISS, cache.get(2));
        assertEquals(2, cache.get(3));
    }

    @Test
    void case_5() {
        cache = new LRUCache(2);
        cache.put(2, 1);
        cache.put(2, 2);

        assertEquals(2, cache.get(2));

        cache.put(1, 1);
        cache.put(4, 1);

        assertEquals(LRUCache.CACHE_MISS, cache.get(2));
    }
}