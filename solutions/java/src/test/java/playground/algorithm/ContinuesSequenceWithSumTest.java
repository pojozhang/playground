package playground.algorithm;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class ContinuesSequenceWithSumTest {

    private ContinuesSequenceWithSum solution = new ContinuesSequenceWithSum();

    @Test
    void case_1() {
        int[][] result = solution.findContinuousSequence(9);

        assertThat(Arrays.asList(result)).containsExactlyInAnyOrder(
                new int[]{2, 3, 4},
                new int[]{4, 5}
        );
    }

    @Test
    void case_2() {
        int[][] result = solution.findContinuousSequence(15);

        assertThat(Arrays.asList(result)).containsExactlyInAnyOrder(
                new int[]{1, 2, 3, 4, 5},
                new int[]{4, 5, 6},
                new int[]{7, 8}
        );
    }
}