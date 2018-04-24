package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LongestSubstringWithoutRepeatingCharactersTest {

    @Test
    void case_1() {
        LongestSubstringWithoutRepeatingCharacters solution = new LongestSubstringWithoutRepeatingCharacters();

        assertEquals(3, solution.lengthOfLongestSubstring("abcabcbb"));
    }

    @Test
    void case_2() {
        LongestSubstringWithoutRepeatingCharacters solution = new LongestSubstringWithoutRepeatingCharacters();

        assertEquals(1, solution.lengthOfLongestSubstring("bbbbb"));
    }

    @Test
    void case_3() {
        LongestSubstringWithoutRepeatingCharacters solution = new LongestSubstringWithoutRepeatingCharacters();

        assertEquals(3, solution.lengthOfLongestSubstring("pwwkew"));
    }

    @Test
    void case_4() {
        LongestSubstringWithoutRepeatingCharacters solution = new LongestSubstringWithoutRepeatingCharacters();

        assertEquals(2, solution.lengthOfLongestSubstring("aab"));
    }

    @Test
    void case_5() {
        LongestSubstringWithoutRepeatingCharacters solution = new LongestSubstringWithoutRepeatingCharacters();

        assertEquals(3, solution.lengthOfLongestSubstring("dvdf"));
    }
}