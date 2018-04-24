package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class HeapSortTest {

    @Test
    void case_1() {
        HeapSort solution = new HeapSort();
        int[] nums = new int[]{3, 4, 2, 0, 1};
        solution.sort(nums);
        assertArrayEquals(new int[]{0, 1, 2, 3, 4}, nums);
    }

    @Test
    void case_2() {
        HeapSort solution = new HeapSort();
        int[] nums = new int[]{1};
        solution.sort(nums);
        assertArrayEquals(new int[]{1}, nums);
    }
}