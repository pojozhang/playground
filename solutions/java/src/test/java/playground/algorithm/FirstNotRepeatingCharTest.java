package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FirstNotRepeatingCharTest {

    private FirstNotRepeatingChar solution = new FirstNotRepeatingChar();

    @Test
    void case_1() {
        assertThat(solution.firstUniqChar("abaccdeff")).isEqualTo('b');
    }
}