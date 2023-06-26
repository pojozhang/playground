package playground.algorithm;

import static playground.util.Utils.swap;

public class BubbleSort {

    public void sort(int[] nums) {
        for (int i = nums.length; i >= 0; i--) {
            for (int j = 0; j < i - 1; j++) {
                if (nums[j] > nums[j + 1]) {
                    swap(nums, j, j + 1);
                }
            }
        }
    }
}
