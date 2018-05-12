package playground.algorithm;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Permutations2Test {

    private Permutations2 solution = new Permutations2();

    @Test
    void case_1() {
        assertThat(solution.permuteUnique(new int[]{1, 1, 2})).containsExactlyInAnyOrder(
                List.of(1, 1, 2),
                List.of(1, 2, 1),
                List.of(2, 1, 1)
        );
    }

    @Test
    void case_2() {
        assertThat(solution.permuteUnique(new int[]{2, 2, 1, 1})).containsExactlyInAnyOrder(
                List.of(1, 1, 2, 2),
                List.of(1, 2, 1, 2),
                List.of(1, 2, 2, 1),
                List.of(2, 1, 1, 2),
                List.of(2, 1, 2, 1),
                List.of(2, 2, 1, 1)
        );
    }
}