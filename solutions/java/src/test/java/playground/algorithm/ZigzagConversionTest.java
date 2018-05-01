package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ZigzagConversionTest {

    private ZigzagConversion solution = new ZigzagConversion();

    @Test
    void case_1() {
        assertEquals("PAHNAPLSIIGYIR", solution.convert("PAYPALISHIRING", 3));
    }

    @Test
    void case_2() {
        assertEquals("PINALSIGYAHRPI", solution.convert("PAYPALISHIRING", 4));
    }

    @Test
    void case_3() {
        assertEquals("AB", solution.convert("AB", 1));
    }
}