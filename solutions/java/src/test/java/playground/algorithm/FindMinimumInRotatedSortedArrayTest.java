package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FindMinimumInRotatedSortedArrayTest {

    private FindMinimumInRotatedSortedArray solution = new FindMinimumInRotatedSortedArray();

    @Test
    void case_1() {
        assertEquals(0, solution.findMin(new int[]{4, 5, 6, 7, 0, 1, 2}));
    }

    @Test
    void case_2() {
        assertEquals(1, solution.findMin(new int[]{1, 2}));
    }
}