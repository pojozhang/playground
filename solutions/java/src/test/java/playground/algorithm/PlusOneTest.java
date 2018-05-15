package playground.algorithm;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class PlusOneTest {

    private PlusOne solution = new PlusOne();

    @Test
    void case_1() {
        assertArrayEquals(new int[]{1, 2, 4}, solution.plusOne(new int[]{1, 2, 3}));
    }

    @Test
    void case_2() {
        assertArrayEquals(new int[]{4, 3, 2, 2}, solution.plusOne(new int[]{4, 3, 2, 1}));
    }

    @Test
    void case_3() {
        assertArrayEquals(new int[]{1, 0}, solution.plusOne(new int[]{9}));
    }

    @Test
    void case_4() {
        assertArrayEquals(new int[]{9, 0, 0, 0}, solution.plusOne(new int[]{8, 9, 9, 9}));
    }
}