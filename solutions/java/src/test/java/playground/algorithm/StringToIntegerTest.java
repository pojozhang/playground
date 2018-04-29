package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringToIntegerTest {

    private StringToInteger solution = new StringToInteger();

    @Test
    void case_1() {
        assertEquals(42, solution.myAtoi("42"));
    }

    @Test
    void case_2() {
        assertEquals(-42, solution.myAtoi("   -42"));
    }

    @Test
    void case_3() {
        assertEquals(4193, solution.myAtoi("4193 with words"));
    }

    @Test
    void case_4() {
        assertEquals(0, solution.myAtoi("words and 987"));
    }

    @Test
    void case_5() {
        assertEquals(-2147483648, solution.myAtoi("-91283472332"));
    }

    @Test
    void case_6() {
        assertEquals(1, solution.myAtoi("+1"));
    }

    @Test
    void case_7() {
        assertEquals(0, solution.myAtoi("+-2"));
    }

    @Test
    void case_8() {
        assertEquals(0, solution.myAtoi("   +0 123"));
    }

    @Test
    void case_9() {
        assertEquals(2147483647, solution.myAtoi("9223372036854775808"));
    }

    @Test
    void case_10() {
        assertEquals(0, solution.myAtoi("0-1"));
    }
}