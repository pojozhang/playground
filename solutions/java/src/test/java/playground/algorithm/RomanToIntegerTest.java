package playground.algorithm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RomanToIntegerTest {

    @Test
    void case_1() {
        RomanToInteger solution = new RomanToInteger();

        assertEquals(3, solution.romanToInt("III"));
    }

    @Test
    void case_2() {
        RomanToInteger solution = new RomanToInteger();

        assertEquals(4, solution.romanToInt("IV"));
    }

    @Test
    void case_3() {
        RomanToInteger solution = new RomanToInteger();

        assertEquals(9, solution.romanToInt("IX"));
    }

    @Test
    void case_4() {
        RomanToInteger solution = new RomanToInteger();

        assertEquals(58, solution.romanToInt("LVIII"));
    }

    @Test
    void case_5() {
        RomanToInteger solution = new RomanToInteger();

        assertEquals(1994, solution.romanToInt("MCMXCIV"));
    }
}