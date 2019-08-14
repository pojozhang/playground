package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MultiplyStringsTest {

    private MultiplyStrings solution = new MultiplyStrings();

    @Test
    void case_1() {
        assertEquals("6", solution.multiply("2", "3"));
    }

    @Test
    void case_2() {
        assertEquals("56088", solution.multiply("123", "456"));
    }

    @Test
    void case_3() {
        assertEquals("81", solution.multiply("9", "9"));
    }

    @Test
    void case_4() {
        assertEquals("0", solution.multiply("9133", "9"));
    }
}