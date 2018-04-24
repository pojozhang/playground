package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BinarySearchTest {

    @Test
    void case_1() {
        BinarySearch solution = new BinarySearch();
        int[] nums = new int[]{0, 1, 2, 3, 4};

        assertEquals(3, solution.search(nums, 3));
    }

    @Test
    void case_2() {
        BinarySearch solution = new BinarySearch();
        int[] nums = new int[]{0, 1, 2, 3, 4};

        assertEquals(-1, solution.search(nums, 6));
    }
}