package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidateStackSequencesTest {

    private ValidateStackSequences solution = new ValidateStackSequences();

    @Test
    void case_1() {
        assertTrue(solution.validateStackSequences(new int[]{1, 2, 3, 4, 5}, new int[]{4, 5, 3, 2, 1}));
    }

    @Test
    void case_2() {
        assertFalse(solution.validateStackSequences(new int[]{1, 2, 3, 4, 5}, new int[]{4, 3, 5, 1, 2}));
    }
}