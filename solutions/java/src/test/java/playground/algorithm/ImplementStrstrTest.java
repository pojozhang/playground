package playground.algorithm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ImplementStrstrTest {

    private ImplementStrstr solution = new ImplementStrstr();

    @Test
    void case_1() {
        assertEquals(0, solution.strStr("a", ""));
    }

    @Test
    void case_2() {
        assertEquals(2, solution.strStr("hello", "ll"));
    }

    @Test
    void case_3() {
        assertEquals(0, solution.strStr("a", "a"));
    }

    @Test
    void case_4() {
        assertEquals(-1, solution.strStr("aaaaa", "bba"));
    }
}