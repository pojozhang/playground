package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LongestConsecutiveSequenceTest {

    @Test
    void case00() {
        int[] nums = {1, 2, 3, 9, 5, 6, 7};
        assertEquals(new LongestConsecutiveSequence().longestConsecutive(nums), 3);
    }


}
