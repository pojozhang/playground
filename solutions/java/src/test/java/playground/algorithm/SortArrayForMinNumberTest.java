package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SortArrayForMinNumberTest {

    private SortArrayForMinNumber solution = new SortArrayForMinNumber();

    @Test
    void case_1() {
        assertThat(solution.minNumber(new int[]{10, 2})).isEqualTo("102");
    }

    @Test
    void case_2() {
        assertThat(solution.minNumber(new int[]{3, 30, 34, 5, 9})).isEqualTo("3033459");
    }
}