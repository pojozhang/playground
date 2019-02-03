package playground.structure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeapTest {

    @Test
    void top_element_should_be_maximum_or_minimum_after_added_to_the_heap() {
        Heap<Integer> heap = new Heap<>();

        heap.addAll(10, 50, 80, 100, 7, 45, 3);

        assertEquals(100, heap.peek().intValue());
        assertEquals(7, heap.size());
    }

    @Test
    void top_element_should_be_maximum_or_minimum_after_previous_peek_is_removed() {
        Heap<Integer> heap = new Heap<>();
        heap.addAll(10, 50, 80, 100, 7, 45, 3);

        Integer peek = heap.poll();

        assertEquals(100, peek.intValue());
        assertEquals(80, heap.peek().intValue());
        assertEquals(6, heap.size());
    }
}