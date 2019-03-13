package playground.algorithm;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CombinationsTest {

    private Combinations solution = new Combinations();

    @Test
    void case_1() {
        List<List<Integer>> result = solution.combine(4, 2);

        assertThat(result).containsExactlyInAnyOrder(
                List.of(2, 4),
                List.of(3, 4),
                List.of(2, 3),
                List.of(1, 2),
                List.of(1, 3),
                List.of(1, 4)
        );
    }
}