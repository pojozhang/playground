package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class QuickSortTest {

    @Test
    public void case_1() {
        QuickSort solution = new QuickSort();
        int[] nums = new int[] { 3, 4, 2, 0, 1 };
        solution.sort(nums);
        assertArrayEquals(new int[] { 0, 1, 2, 3, 4 }, nums);
    }

    @Test
    public void case_2() {
        QuickSort solution = new QuickSort();
        int[] nums = new int[] { 1 };
        solution.sort(nums);
        assertArrayEquals(new int[] { 1 }, nums);
    }
}