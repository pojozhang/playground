package playground.algorithm;

public class MaximumSubArray {

    public int maxSubArray(int[] nums) {
        int max = nums[0], sum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            // 如果之前的和加起来是负数，并且当前数字大于之前的和，那么重置sum为当前的数字。
            if (sum < 0 && nums[i] > sum) {
                sum = nums[i];
            } else {
                sum += nums[i];
            }
            max = Math.max(max, sum);
        }
        return max;
    }
}