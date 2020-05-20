package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class KLeastNumbersTest {

    private KLeastNumbers solution = new KLeastNumbers();

    @Test
    void case_1() {
        int[] result = solution.getLeastNumbers(new int[]{3, 2, 1}, 2);

        assertThat(result).containsExactlyInAnyOrder(1, 2);
    }

    @Test
    void case_2() {
        int[] result = solution.getLeastNumbers(new int[]{3, 2, 1}, 2);

        assertThat(result).containsExactlyInAnyOrder(1, 2);
    }
}