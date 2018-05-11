package playground.algorithm;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class ThreeSumTest {

    private ThreeSum solution = new ThreeSum();

    @Test
    void case_1() {
        List<List<Integer>> result = solution.threeSum(new int[]{-1, 0, 1, 2, -1, -4});

        assertThat(result).containsExactlyInAnyOrder(List.of(-1, -1, 2), List.of(-1, 0, 1));
    }
}