package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SequenceOfBSTTest {

    private SequenceOfBST solution = new SequenceOfBST();

    @Test
    void case_1() {
        assertFalse(solution.verifyPostorder(new int[]{1, 6, 3, 2, 5}));
    }

    @Test
    void case_2() {
        assertTrue(solution.verifyPostorder(new int[]{1, 3, 2, 6, 5}));
    }
}