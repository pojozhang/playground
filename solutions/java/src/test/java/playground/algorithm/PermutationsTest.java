package playground.algorithm;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class PermutationsTest {

    private Permutations solution = new Permutations();

    @Test
    void case_1() {
        List<List<Integer>> result = solution.permute(new int[]{1, 2, 3});

        assertThat(result).containsExactlyInAnyOrder(
            List.of(1, 2, 3),
            List.of(1, 3, 2),
            List.of(2, 1, 3),
            List.of(2, 3, 1),
            List.of(3, 1, 2),
            List.of(3, 2, 1)
        );
    }
}