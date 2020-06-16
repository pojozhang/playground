package playground.algorithm;

import java.util.Arrays;

public class ContinuousCards {

    public boolean isStraight(int[] nums) {
        Arrays.sort(nums);
        int zeros = 0;
        if (nums[0] == 0) {
            zeros++;
        }
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == 0) {
                zeros++;
                continue;
            }
            if (nums[i - 1] == 0 || nums[i] == nums[i - 1] + 1) {
                continue;
            }
            if (nums[i] == nums[i - 1]) {
                return false;
            }
            zeros -= nums[i] - nums[i - 1] - 1;
            if (zeros < 0) {
                return false;
            }
        }
        return true;
    }
}
