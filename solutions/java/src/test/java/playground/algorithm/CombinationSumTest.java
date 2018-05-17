package playground.algorithm;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CombinationSumTest {

    private CombinationSum solution = new CombinationSum();

    @Test
    void case_1() {
        assertThat(solution.combinationSum(new int[]{2, 3, 6, 7}, 7))
                .containsExactlyInAnyOrder(List.of(7), List.of(2, 2, 3));
    }

    @Test
    void case_2() {
        assertThat(solution.combinationSum(new int[]{2, 3, 5}, 8))
                .containsExactlyInAnyOrder(List.of(2, 2, 2, 2), List.of(2, 3, 3), List.of(3, 5));
    }
}