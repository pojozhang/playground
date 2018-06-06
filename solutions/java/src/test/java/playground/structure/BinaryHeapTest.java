package playground.structure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

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
        Assertions.assertArrayEquals(new Integer[]{1, 4, 2, 29, 6, 7, 8, 35},binaryHeap.getArray());

        binaryHeap.deleteMin();
        Assertions.assertArrayEquals(new Integer[]{2, 4, 7, 29, 6, 35, 8},binaryHeap.getArray());
    }


    @Test
    void case04() {
        BinaryHeapInList<Integer> binaryHeap = new BinaryHeapInList<>(new Integer[]{1, 4, 2, 35, 6, 7, 8, 29});
        Assertions.assertEquals(Integer.valueOf(1), binaryHeap.findMin());
    }

    @Test
    void case05() {
        BinaryHeapInList<Integer> binaryHeap = new BinaryHeapInList<>(new Integer[]{1, 4, 2, 35, 6, 7, 8, 29});
        Assertions.assertEquals(Integer.valueOf(1), binaryHeap.deleteMin());
        Assertions.assertEquals(Integer.valueOf(2), binaryHeap.findMin());
    }

    @Test
    void case06() {

        BinaryHeapInList<Integer> binaryHeap = new BinaryHeapInList<>(new Integer[]{1, 4, 2, 35, 6, 7, 8, 29});
        Assertions.assertIterableEquals(List.of(1, 4, 2, 29, 6, 7, 8), binaryHeap.getArray());

        binaryHeap.deleteMin();
        Assertions.assertIterableEquals(List.of(2, 4, 7, 29, 6, 35), binaryHeap.getArray());
    }

}
