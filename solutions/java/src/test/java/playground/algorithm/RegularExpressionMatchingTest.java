package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RegularExpressionMatchingTest {

    private RegularExpressionMatching solution = new RegularExpressionMatching();

    @Test
    void case_1() {
        assertThat(solution.isMatch("aa", "a")).isFalse();
    }

    @Test
    void case_2() {
        assertThat(solution.isMatch("aa", "a*")).isTrue();
    }

    @Test
    void case_3() {
        assertThat(solution.isMatch("ab", ".*")).isTrue();
    }

    @Test
    void case_4() {
        assertThat(solution.isMatch("aab", "c*a*b")).isTrue();
    }

    @Test
    void case_5() {
        assertThat(solution.isMatch("mississippi", "mis*is*p*.")).isFalse();
    }

    @Test
    void case_6() {
        assertThat(solution.isMatch("ab", ".*c")).isFalse();
    }
}