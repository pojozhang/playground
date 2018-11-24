package playground.algorithm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LRUCacheTest {

    private static final int CAPACITY = 10;
    private static final int KEY = 1;
    private LRUCache cache;

    @BeforeEach
    void setUp() {
        cache = new LRUCache(CAPACITY);
    }

    @Test
    void case_1() {
        assertEquals(LRUCache.CACHE_MISS, cache.get(KEY));
    }

    @Test
    void case_2() {
        cache.put(KEY, 1);

        assertEquals(1, cache.get(KEY));
    }
}