package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SlidingWindowMaximumTest {

    private SlidingWindowMaximum solution = new SlidingWindowMaximum();

    @Test
    void case_1() {
        assertArrayEquals(new int[]{3, 3, 5, 5, 6, 7}, solution.maxSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3));
    }

    @Test
    void case_2() {
        assertArrayEquals(new int[]{}, solution.maxSlidingWindow(new int[]{}, 0));
    }

    @Test
    void case_3() {
        assertArrayEquals(new int[]{1, -1}, solution.maxSlidingWindow(new int[]{1, -1}, 1));
    }

    @Test
    void case_4() {
        assertArrayEquals(new int[]{3, 3, 2, 5}, solution.maxSlidingWindow(new int[]{1, 3, 1, 2, 0, 5}, 3));
    }
}