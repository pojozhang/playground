package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PermutationInStringTest {

    private PermutationInString solution = new PermutationInString();

    @Test
    void case_1() {
        assertTrue(solution.checkInclusion("ab", "eidbaooo"));
    }

    @Test
    void case_2() {
        assertFalse(solution.checkInclusion("ab", "eidboaoo"));
    }
}