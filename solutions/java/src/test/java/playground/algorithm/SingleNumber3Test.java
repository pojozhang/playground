package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SingleNumber3Test {

    private SingleNumber3 solution = new SingleNumber3();

    @Test
    void case_1() {
        int[] result = solution.singleNumber(new int[]{1, 2, 1, 3, 2, 5});

        assertThat(result).containsExactlyInAnyOrder(3, 5);
    }

}