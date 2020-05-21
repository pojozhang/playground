package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaximumSubArrayTest {

    private MaximumSubArray solution = new MaximumSubArray();

    @Test
    void case_1() {
        assertEquals(6, solution.maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
    }

    @Test
    void case_2() {
        assertEquals(1, solution.maxSubArray(new int[]{-2, 1}));
    }

    @Test
    void case_3() {
        assertEquals(-1, solution.maxSubArray(new int[]{-1, -1, -2, -2}));
    }
}