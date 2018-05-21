package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumberOf1BitsTest {

    private NumberOf1Bits solution = new NumberOf1Bits();

    @Test
    void case_1() {
        assertEquals(3, solution.hammingWeight(11));
    }

    @Test
    void case_2() {
        assertEquals(1, solution.hammingWeight(128));
    }
}