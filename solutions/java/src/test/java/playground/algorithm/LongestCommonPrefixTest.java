package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LongestCommonPrefixTest {

    @Test
    void case_1() {
        LongestCommonPrefix solution = new LongestCommonPrefix();

        String prefix = solution.longestCommonPrefix(new String[]{"flower", "flow", "flight"});

        assertEquals("fl", prefix);
    }

    @Test
    void case_2() {
        LongestCommonPrefix solution = new LongestCommonPrefix();

        String prefix = solution.longestCommonPrefix(new String[]{"dog", "racecar", "car"});

        assertEquals("", prefix);
    }

    @Test
    void case_3() {
        LongestCommonPrefix solution = new LongestCommonPrefix();

        String prefix = solution.longestCommonPrefix(new String[]{});

        assertEquals("", prefix);
    }
}