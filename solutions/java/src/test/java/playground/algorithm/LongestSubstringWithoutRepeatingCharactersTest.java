package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LongestSubstringWithoutRepeatingCharactersTest {

    @Test
    public void case_1() {
        LongestSubstringWithoutRepeatingCharacters solution = new LongestSubstringWithoutRepeatingCharacters();

        assertEquals(3, solution.lengthOfLongestSubstring("abcabcbb"));
    }

    @Test
    public void case_2() {
        LongestSubstringWithoutRepeatingCharacters solution = new LongestSubstringWithoutRepeatingCharacters();

        assertEquals(1, solution.lengthOfLongestSubstring("bbbbb"));
    }

    @Test
    public void case_3() {
        LongestSubstringWithoutRepeatingCharacters solution = new LongestSubstringWithoutRepeatingCharacters();

        assertEquals(3, solution.lengthOfLongestSubstring("pwwkew"));
    }

    @Test
    public void case_4() {
        LongestSubstringWithoutRepeatingCharacters solution = new LongestSubstringWithoutRepeatingCharacters();

        assertEquals(2, solution.lengthOfLongestSubstring("aab"));
    }

    @Test
    public void case_5() {
        LongestSubstringWithoutRepeatingCharacters solution = new LongestSubstringWithoutRepeatingCharacters();

        assertEquals(3, solution.lengthOfLongestSubstring("dvdf"));
    }
}