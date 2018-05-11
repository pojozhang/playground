package playground.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FourSum {


    public List<List<Integer>> fourSum(int[] nums, int target) {

        Arrays.sort(nums);

        System.out.println(Arrays.toString(nums));

        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0, length = nums.length; i < length - 1; i++) {

            List<List<Integer>> threeSum = threeSum(nums, i + 1, target - nums[i]);
            result.addAll(append(nums[i], threeSum));

        }

        return result;
    }


    public List<List<Integer>> threeSum(int[] nums, int start, int target) {

        List<List<Integer>> result = new ArrayList<>();

        for (int i = start, length = nums.length; i < length - 1; i++) {

            List<List<Integer>> twoSum = twoSum(nums, i + 1, target - nums[i]);

            System.out.println(twoSum);

            result.addAll(append(nums[i], twoSum));

        }

        return result;
    }

    public List<List<Integer>> twoSum(int[] nums, int start, int target) {

        List<List<Integer>> result = new ArrayList<>();

        int l = start;
        int r = nums.length - 1;

        Integer tmp_l = null;
        Integer tmp_r = null;

        while (l < r) {

            if (nums[l] + nums[r] == target) {

                if (tmp_l == null || tmp_r == null || nums[l] != tmp_l || nums[r] != tmp_r) {
                    result.add(Arrays.asList(nums[l], nums[r]));
                    tmp_l = nums[l];
                    tmp_r = nums[r];
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
