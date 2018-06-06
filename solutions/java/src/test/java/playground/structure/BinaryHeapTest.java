package playground.structure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BinaryHeapTest {

    @Test
    void case01() {
        BinaryHeap<Integer> binaryHeap = new BinaryHeap<>(new Integer[]{1, 4, 2, 35, 6, 7, 8, 29});
        Assertions.assertEquals(Integer.valueOf(1), binaryHeap.findMin());
    }

    @Test
    void case02() {
        BinaryHeap<Integer> binaryHeap = new BinaryHeap<>(new Integer[]{1, 4, 2, 35, 6, 7, 8, 29});
        Assertions.assertEquals(Integer.valueOf(1), binaryHeap.deleteMin());
        Assertions.assertEquals(Integer.valueOf(2), binaryHeap.findMin());
    }

    @Test
    void case03() {
        BinaryHeap<Integer> binaryHeap = new BinaryHeap<>(new Integer[]{1, 4, 2, 35, 6, 7, 8, 29});
        Assertions.assertArrayEquals(
                new Integer[]{null, 1, 4, 2, 29, 6, 7, 8, 35, null},
                binaryHeap.getArray());
    }

}
