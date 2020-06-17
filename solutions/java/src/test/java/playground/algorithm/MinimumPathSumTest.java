package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MinimumPathSumTest {

    private MinimumPathSum solution = new MinimumPathSum();

    @Test
    void case_1() {
        int result = solution.minPathSum(new int[][]{
                {1, 3, 1},
                {1, 5, 1},
                {4, 2, 1}
        });

        assertThat(result).isEqualTo(7);
    }

    @Test
    void case_2() {
        int result = solution.minPathSum(new int[][]{
                {1, 2, 5},
                {3, 2, 1}
        });

        assertThat(result).isEqualTo(6);
    }
}