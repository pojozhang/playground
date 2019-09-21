package playground.algorithm;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SubsetsTest {

    private Subsets solution = new Subsets();

    @Test
    void case_1() {
        List<List<Integer>> subsets = solution.subsets(new int[]{1, 2, 3});

        assertThat(subsets).containsExactlyInAnyOrder(
                List.of(3),
                List.of(1),
                List.of(2),
                List.of(1, 2, 3),
                List.of(1, 3),
                List.of(2, 3),
                List.of(1, 2),
                List.of());
    }
}