package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class MergeSortTest {

    private MergeSort solution = new MergeSort();

    @Test
    void case_1() {
        int[] nums = new int[]{4, 3, 0, 1, 2};

        int[] result = solution.sort(nums);

        assertArrayEquals(new int[]{0, 1, 2, 3, 4}, result);
    }
}