package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class TwoSumTest {

    @Test
    public void case_1() {
        TwoSum twoSum = new TwoSum();
        int[] nums = new int[]{2, 1, 7, 20};
        int target = 9;

        assertArrayEquals(new int[]{0, 2}, twoSum.twoSum(nums, target));
    }

    @Test
    public void case_2() {
        TwoSum twoSum = new TwoSum();
        int[] nums = new int[]{1, 5, 5, 20};
        int target = 10;

        assertArrayEquals(new int[]{1, 2}, twoSum.twoSum(nums, target));
    }
}