package playground.algorithm;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class FourSumTest {

    @Test
    void should_find_two_sum() {

        int[] nums = {3, 2, 2, 3, 4, 4, 4, 4, 4};

        Arrays.sort(nums);

        List<List<Integer>> result = new FourSum().twoSum(nums, 0, 6);

        Assertions.assertThat(result).containsExactlyInAnyOrder(List.of(2, 4), List.of(3, 3));
    }

    @Test
    void should_find_three_sum() {

        int[] nums = {2, 3, 3, 2, 3, 4, 4, 4, 4, 6, 1};

        Arrays.sort(nums);
        System.out.println(Arrays.toString(nums));

        List<List<Integer>> result = new FourSum().threeSum(nums, 0, 9);

        System.out.println(result);

//        Assertions.assertThat(result).containsExactlyInAnyOrder(List.of(1, 4, 4), List.of(2, 3, 4));

    }

    @Test
    void should_find_four_sum() {

        int[] nums = {3, 2, 2, 3, 3, 3, 4, 4, 4, 1, 9};

        List<List<Integer>> result = new FourSum().fourSum(nums, 12);

        System.out.println(result);
//        Assertions.assertThat(result).containsExactlyInAnyOrder(List.of(1, 4, 4), List.of(2, 3, 4));
    }

}
