package playground.algorithm;

public class MaximumSubArray {

    public int maxSubArray(int[] nums) {
        int max = Integer.MIN_VALUE, tempSum = 0;
        for (int i = 0; i < nums.length; i++) {
            tempSum += nums[i];
            if (tempSum > max) {
                max = tempSum;
            }
            if (tempSum < 0) {
                tempSum = 0;
            }
        }
        return max;
    }
}