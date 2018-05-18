package playground.algorithm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PowTest {

    private Pow solution = new Pow();

    @Test
    void case_1() {
        assertEquals(1024.00000, solution.myPow(2.00000, 10));
    }

    @Test
    void case_2() {
        assertEquals(9.26100, solution.myPow(2.10000, 3), 0.0000001);
    }

    @Test
    void case_3() {
        assertEquals(0.25000, solution.myPow(2.00000, -2));
    }

    @Test
    void case_4() {
        assertEquals(0, solution.myPow(2.00000, -2147483648));
    }

    @Test
    void case_5() {
        assertEquals(1.0, solution.myPow(1.00000, -2147483648));
    }

    @Test
    void case_6() {
        assertEquals(1.0, solution.myPow(-1.00000, -2147483648));
    }
}