package playground.algorithm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class LongestPalindromicSubstringTest {

    private LongestPalindromicSubstring solution = new LongestPalindromicSubstring();

    @Test
    void case_1() {
        assertEquals("bab", solution.longestPalindrome("babad"));
    }

    @Test
    void case_2() {
        assertEquals("bb", solution.longestPalindrome("bb"));
    }
}