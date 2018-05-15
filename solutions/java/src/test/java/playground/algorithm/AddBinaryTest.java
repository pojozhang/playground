package playground.algorithm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AddBinaryTest {

    private AddBinary solution = new AddBinary();

    @Test
    void case_1() {
        assertEquals("100", solution.addBinary("11", "1"));
    }

    @Test
    void case_2() {
        assertEquals("10101", solution.addBinary("1010", "1011"));
    }

    @Test
    void case_3() {
        assertEquals("11110", solution.addBinary("1111", "1111"));
    }

    @Test
    void case_4() {
        assertEquals("110110", solution.addBinary("100", "110010"));
    }
}