package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FirstUniqueCharacterInAStringTest {

    private FirstUniqueCharacterInAString solution = new FirstUniqueCharacterInAString();

    @Test
    void case_1() {
        assertThat(solution.firstUniqChar("leetcode")).isEqualTo(0);
    }

    @Test
    void case_2() {
        assertThat(solution.firstUniqChar("loveleetcode")).isEqualTo(2);
    }
}