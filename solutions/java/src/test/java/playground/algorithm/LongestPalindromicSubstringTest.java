package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LongestPalindromicSubstringTest {

    @Test
    void case_1() {
        LongestPalindromicSubstring solution = new LongestPalindromicSubstring();

        assertEquals("bab", solution.longestPalindrome("babad"));
    }

    @Test
    void case_2() {
        LongestPalindromicSubstring solution = new LongestPalindromicSubstring();

        assertEquals("bb", solution.longestPalindrome("bb"));
    }
}