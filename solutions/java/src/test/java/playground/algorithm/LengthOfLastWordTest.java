package playground.algorithm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LengthOfLastWordTest {

    private LengthOfLastWord solution = new LengthOfLastWord();

    @Test
    void case_1() {
        assertEquals(5, solution.lengthOfLastWord("Hello World"));
    }

    @Test
    void case_2() {
        assertEquals(1, solution.lengthOfLastWord("a "));
    }
}