package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FindMinimumInRotatedSortedArrayIITest {

    private FindMinimumInRotatedSortedArrayII solution = new FindMinimumInRotatedSortedArrayII();

    @Test
    void case_1() {
        assertEquals(0, solution.findMin(new int[]{4, 5, 6, 7, 0, 1, 2}));
    }

    @Test
    void case_2() {
        assertEquals(0, solution.findMin(new int[]{2, 2, 2, 0, 1}));
    }

    @Test
    void case_3() {
        assertEquals(1, solution.findMin(new int[]{3, 1, 3}));
    }

    @Test
    void case_4() {
        assertEquals(1, solution.findMin(new int[]{10, 1, 10, 10, 10}));
    }
}