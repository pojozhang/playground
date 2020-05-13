package playground.algorithm;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class NQueensTest {

    private NQueens solution = new NQueens();

    @Test
    void case_1() {
        List<List<String>> result = solution.solveNQueens(4);

        assertThat(result).containsExactlyInAnyOrder(
                List.of(".Q..",
                        "...Q",
                        "Q...",
                        "..Q."),
                List.of("..Q.",
                        "Q...",
                        "...Q",
                        ".Q..")
        );
    }

}