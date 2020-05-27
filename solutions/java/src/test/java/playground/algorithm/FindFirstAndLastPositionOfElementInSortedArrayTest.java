package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FindFirstAndLastPositionOfElementInSortedArrayTest {

    private FindFirstAndLastPositionOfElementInSortedArray solution = new FindFirstAndLastPositionOfElementInSortedArray();

    @Test
    void case_1() {
        int[] result = solution.searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8);

        assertThat(result).containsExactly(3, 4);
    }

    @Test
    void case_2() {
        int[] result = solution.searchRange(new int[]{5, 7, 7, 8, 8, 10}, 6);

        assertThat(result).containsExactly(-1, -1);
    }
}