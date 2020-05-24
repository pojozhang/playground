package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UglyNumberIITest {

    private UglyNumberII solution = new UglyNumberII();

    @Test
    void case_1() {
        assertThat(solution.nthUglyNumber(10)).isEqualTo(12);
    }
}