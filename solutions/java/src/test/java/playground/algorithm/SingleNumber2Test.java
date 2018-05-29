package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SingleNumber2Test {

    private SingleNumber2 solution = new SingleNumber2();

    @Test
    void case_1() {
        assertEquals(3, solution.singleNumber(new int[]{2, 2, 3, 2}));
    }

    @Test
    void case_2() {
        assertEquals(99, solution.singleNumber(new int[]{0, 1, 0, 1, 0, 1, 99}));
    }

    @Test
    void case_3() {
        assertEquals(-4, solution.singleNumber(new int[]{-2, -2, 1, 1, -3, 1, -3, -3, -4, -2}));
    }
}