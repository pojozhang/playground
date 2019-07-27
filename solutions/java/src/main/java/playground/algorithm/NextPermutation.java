package playground.algorithm;

import static playground.util.Utils.swap;

public class NextPermutation {

    // 1. 从右向左遍历，找到第一个nums[i]比nums[i-1]大的数字。
    // 2. 位于nums[i]右边的数字是递减（或相等）的，比如数字123432，4右边的数字是递减（或相等）的。
    // 3. 从nums[i]开始往右找到刚好比nums[i-1]大的数字，由于nums[i]右边的数字是递减（或相等）的，因此只要找到到第一个小于nums[i-1]的数字，然后取其左边的数字即可。
    // 4. 把找到的数字和nums[i-1]交换。
    // 5. 对数组中i之后的元素进行翻转，比如数字132进行上面1-4的步骤后得到231，再对31部分进行翻转得到13，因此最后结果是213。
    public void nextPermutation(int[] nums) {
        for (int i = nums.length - 1; i > 0; i--) {
            if (nums[i - 1] < nums[i]) {
                int j = i + 1;
                for (; j < nums.length && nums[j] > nums[i - 1]; j++) ;
                swap(nums, j - 1, i - 1);
                reverse(nums, i, nums.length);
                return;
            }
        }
        // 整个序列是递减的，因此只要翻转数组。
        reverse(nums, 0, nums.length);
    }

    private void reverse(int[] nums, int fromIndex, int toIndex) {
        int middleIndex = (fromIndex + toIndex - 1) / 2;
        for (int i = fromIndex; i <= middleIndex; i++) {
            swap(nums, i, toIndex - (i - fromIndex) - 1);
        }
    }
}
