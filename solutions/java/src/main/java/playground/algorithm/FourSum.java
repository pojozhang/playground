package playground.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FourSum {


    public List<List<Integer>> fourSum(int[] nums, int target) {

        Arrays.sort(nums);

        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0, length = nums.length; i < length - 1; i++) {

            if (i == 0 || nums[i] != nums[i - 1]) {
                result.addAll(append(nums[i], threeSum(nums, i + 1, target - nums[i])));
            }

        }

        return result;
    }


    public List<List<Integer>> threeSum(int[] nums, int start, int target) {

        List<List<Integer>> result = new ArrayList<>();

        for (int i = start, length = nums.length; i < length - 1; i++) {

            if (i == start || nums[i] != nums[i - 1]) {
                result.addAll(append(nums[i], twoSum(nums, i + 1, target - nums[i])));
            }

        }

        return result;
    }

    public List<List<Integer>> twoSum(int[] nums, int start, int target) {

        List<List<Integer>> result = new ArrayList<>();

        int l = start;
        int r = nums.length - 1;

        while (l < r) {

            if (nums[l] + nums[r] == target) {

                if (l == start || nums[l] != nums[l - 1]) {
                    result.add(Arrays.asList(nums[l], nums[r]));
                }

                l++;
                r--;
            } else if (nums[l] + nums[r] < target) {
                l++;
            } else if (nums[l] + nums[r] > target) {
                r--;
            }
        }
        return result;
    }


    private List<List<Integer>> append(int i, List<List<Integer>> target) {
        List<List<Integer>> result = new ArrayList<>();

        for (List<Integer> aTarget : target) {
            List<Integer> tmp = new ArrayList<>();
            tmp.add(i);
            tmp.addAll(aTarget);
            result.add(tmp);
        }

        return result;
    }

}
