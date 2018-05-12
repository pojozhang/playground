package playground.algorithm;

import playground.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Permutations2 {

    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        permuteUnique(nums, 0, result);
        return result;
    }

    private void permuteUnique(int[] nums, int start, List<List<Integer>> result) {
        if (start == nums.length - 1) {
            result.add(Arrays.stream(nums).boxed().collect(Collectors.toList()));
            return;
        }

        for (int i = start; i < nums.length; i++) {
            if (isDuplicate(nums, start, i, nums[i])) {
                continue;
            }
            Utils.swap(nums, start, i);
            permuteUnique(nums, start + 1, result);
            Utils.swap(nums, start, i);
        }
    }

    private boolean isDuplicate(int[] nums, int start, int end, int target) {
        for (; start < end; start++) {
            if (nums[start] == target) {
                return true;
            }
        }
        return false;
    }
}
