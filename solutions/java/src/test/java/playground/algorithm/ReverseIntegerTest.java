package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReverseIntegerTest {

    @Test
    void case_1() {
        ReverseInteger solution = new ReverseInteger();

        assertEquals(21, solution.reverse(120));
    }

    @Test
    void case_2() {
        ReverseInteger solution = new ReverseInteger();

        assertEquals(321, solution.reverse(123));
    }

    @Test
    void case_3() {
        ReverseInteger solution = new ReverseInteger();

        assertEquals(-321, solution.reverse(-123));
    }

    @Test
    void case_4() {
        ReverseInteger solution = new ReverseInteger();

        assertEquals(0, solution.reverse(-2147483648));
    }

    @Test
    void case_5() {
        ReverseInteger solution = new ReverseInteger();

        assertEquals(0, solution.reverse(1534236469));
    }
}