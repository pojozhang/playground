package playground.algorithm;

import java.util.Arrays;

public class ThreeSumClosest {

    public int threeSumClosest(int[] nums, int target) {
        int closest = nums[0] + nums[1] + nums[2], sum;
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            for (int l = i + 1, r = nums.length - 1; l < r; ) {
                sum = nums[i] + nums[l] + nums[r];
                if (sum == target) {
                    return sum;
                }
                if (sum < target) {
                    l++;
                }
                if (sum > target) {
                    r--;
                }
                if (Math.abs(target - sum) <= Math.abs(target - closest)) {
                    closest = sum;
                }
            }
        }
        return closest;
    }
}
