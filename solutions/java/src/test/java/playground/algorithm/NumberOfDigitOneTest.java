package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NumberOfDigitOneTest {

    private NumberOfDigitOne solution = new NumberOfDigitOne();

    @Test
    void case_1() {
        assertThat(solution.countDigitOne(13)).isEqualTo(6);
    }
}