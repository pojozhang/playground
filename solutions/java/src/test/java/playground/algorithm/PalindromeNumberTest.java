package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

class PalindromeNumberTest {

    @Test
    public void case_1() {
        PalindromeNumber solution = new PalindromeNumber();
       
        assertFalse(solution.isPalindrome(-121));
    }

    @Test
    public void case_2() {
        PalindromeNumber solution = new PalindromeNumber();
       
        assertTrue(solution.isPalindrome(121));
    }

    @Test
    public void case_3() {
        PalindromeNumber solution = new PalindromeNumber();
       
        assertTrue(solution.isPalindrome(1));
    }

    @Test
    public void case_4() {
        PalindromeNumber solution = new PalindromeNumber();
       
        assertTrue(solution.isPalindrome(1000000001));
    }
}