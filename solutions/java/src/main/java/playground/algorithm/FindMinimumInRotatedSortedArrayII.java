package playground.algorithm;

public class FindMinimumInRotatedSortedArrayII {

    public int findMin(int[] nums) {
        int low = 0, high = nums.length - 1;
        if (nums[low] < nums[high]) {
            return nums[low];
        }
        while (high - low > 1) {
            int middle = (low + high) / 2;
            // 如果两端的值和中间值都相等，那么从头遍历一遍。
            if (nums[middle] == nums[low] && nums[middle] == nums[high]) {
                for (int i = low; i <= high; i++) {
                    if (nums[i] < nums[low]) {
                        return nums[i];
                    }
                }
                return nums[low];
            }
            if (nums[middle] >= nums[low]) {
                low = middle;
                continue;
            }
            high = middle;
        }
        return nums[high];
    }
}
