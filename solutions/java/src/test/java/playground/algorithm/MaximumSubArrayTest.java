package playground.algorithm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

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
}