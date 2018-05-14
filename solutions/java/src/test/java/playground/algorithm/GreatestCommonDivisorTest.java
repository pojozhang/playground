package playground.algorithm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GreatestCommonDivisorTest {

    private GreatestCommonDivisor solution = new GreatestCommonDivisor();

    @Test
    void case_1() {
        assertEquals(5, solution.gcd(10, 5));
    }
}