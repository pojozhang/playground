package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ContinuousCardsTest {

    private ContinuousCards solution = new ContinuousCards();

    @Test
    void case_1() {
        assertThat(solution.isStraight(new int[]{1, 2, 3, 4, 5})).isTrue();
    }

    @Test
    void case_2() {
        assertThat(solution.isStraight(new int[]{0, 0, 1, 2, 5})).isTrue();
    }

    @Test
    void case_3() {
        assertThat(solution.isStraight(new int[]{0, 0, 2, 2, 5})).isFalse();
    }

    @Test
    void case_4() {
        assertThat(solution.isStraight(new int[]{1, 2, 12, 0, 3})).isFalse();
    }

    @Test
    void case_5() {
        assertThat(solution.isStraight(new int[]{0, 0, 8, 5, 4})).isTrue();
    }
}