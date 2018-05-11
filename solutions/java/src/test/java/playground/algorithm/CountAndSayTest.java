package playground.algorithm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CountAndSayTest {

    private CountAndSay solution = new CountAndSay();

    @Test
    void case_1() {
        assertEquals("1211", solution.countAndSay(4));
    }
}