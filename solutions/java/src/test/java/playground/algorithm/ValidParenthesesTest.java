package playground.algorithm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ValidParenthesesTest {

    @Test
    void case_1() {
        ValidParentheses solution = new ValidParentheses();

        assertTrue(solution.isValid("()"));
    }

    @Test
    void case_2() {
        ValidParentheses solution = new ValidParentheses();

        assertFalse(solution.isValid("]"));
    }
}