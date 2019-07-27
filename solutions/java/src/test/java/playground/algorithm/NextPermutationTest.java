package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NextPermutationTest {

    private NextPermutation solution = new NextPermutation();

    @Test
    void case_1() {
        int[] nums = {1, 2, 3};

        solution.nextPermutation(nums);

        assertArrayEquals(new int[]{1, 3, 2}, nums);
    }

    @Test
    void case_2() {
        int[] nums = {3, 2, 1};

        solution.nextPermutation(nums);

        assertArrayEquals(new int[]{1, 2, 3}, nums);
    }

    @Test
    void case_3() {
        int[] nums = {1, 1, 5};

        solution.nextPermutation(nums);

        assertArrayEquals(new int[]{1, 5, 1}, nums);
    }

    @Test
    void case_4() {
        int[] nums = {1, 3, 2};

        solution.nextPermutation(nums);

        assertArrayEquals(new int[]{2, 1, 3}, nums);
    }
}