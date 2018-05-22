package playground.algorithm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class LongestConsecutiveSequenceTest {

    private LongestConsecutiveSequence solution = new LongestConsecutiveSequence();

    @Test
    void case_1() {
        assertEquals(4, solution.longestConsecutive(new int[]{100, 4, 200, 1, 3, 2}));
    }
}