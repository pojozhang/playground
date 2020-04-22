package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SortArrayByParityTest {

    private SortArrayByParity solution = new SortArrayByParity();

    @Test
    public void case_1() {
        int[] input = {2, 4, 3, 1};

        int[] output = solution.sortArrayByParity(input);

        assertThat(output).containsExactlyInAnyOrder(input);
        assertThat(output[0] % 2).isEqualTo(0);
        assertThat(output[1] % 2).isEqualTo(0);
        assertThat(output[2] % 2).isEqualTo(1);
        assertThat(output[3] % 2).isEqualTo(1);
    }

    @Test
    public void case_2() {
        int[] input = {1};

        int[] output = solution.sortArrayByParity(input);

        assertThat(output).containsExactlyInAnyOrder(input);
        assertThat(output[0] % 2).isEqualTo(1);
    }
}