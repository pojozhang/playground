package playground.algorithm;

public class FindMinimumInRotatedSortedArray {

    public int findMin(int[] nums) {
        int low = 0, high = nums.length - 1;
        if (nums[low] <= nums[high]) {
            return nums[low];
        }
        while (high - low > 1) {
            int middle = (low + high) / 2;
            if (nums[middle] > nums[low]) {
                low = middle;
                continue;
            }
            high = middle;
        }
        return nums[high];
    }
}
