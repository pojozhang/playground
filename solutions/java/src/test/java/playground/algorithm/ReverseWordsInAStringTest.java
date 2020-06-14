package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReverseWordsInAStringTest {

    private ReverseWordsInAString solution = new ReverseWordsInAString();

    @Test
    void case_1() {
        assertThat(solution.reverseWords("the sky is blue")).isEqualTo("blue is sky the");
    }

    @Test
    void case_2() {
        assertThat(solution.reverseWords("  hello world!  ")).isEqualTo("world! hello");
    }
}