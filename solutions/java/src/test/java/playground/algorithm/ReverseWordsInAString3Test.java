package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReverseWordsInAString3Test {

    private ReverseWordsInAString3 solution = new ReverseWordsInAString3();

    @Test
    void case_1() {
        assertEquals("s'teL ekat edoCteeL tsetnoc", solution.reverseWords("Let's take LeetCode contest"));
    }
}