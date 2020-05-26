package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InversePairsTest {

    @Test
    void case_1() {
        assertThat(new InversePairs().reversePairs(new int[]{7, 5, 6, 4})).isEqualTo(5);
    }
}