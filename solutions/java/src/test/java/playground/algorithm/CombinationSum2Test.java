package playground.algorithm;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CombinationSum2Test {

    private CombinationSum2 solution = new CombinationSum2();

    @Test
    void case_1() {
        assertThat(solution.combinationSum2(new int[]{10, 1, 2, 7, 6, 1, 5}, 8))
                .containsExactlyInAnyOrder(
                        List.of(1, 7),
                        List.of(1, 2, 5),
                        List.of(2, 6),
                        List.of(1, 1, 6)
                );
    }
}