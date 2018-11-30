package playground.algorithm;

public class SearchInRotatedSortedArray {

    public int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int middle = left + ((right - left) >> 1);
            if (nums[middle] == target) {
                return middle;
            }
            if (nums[left]
                <= nums[middle]) { // 这种情况下数组下面[left, middle]区间内元素递增，包括只有1个元素时的情况，此时nums[left] = nums[middle]。
                if (nums[left] <= target && target < nums[middle]) {
                    right = middle - 1;
                } else {
                    left = middle + 1;
                }
            } else { // 这种情况下数组下面[left, middle]区间内元素递增后递减。
                if (nums[middle] < target && target <= nums[right]) {
                    left = middle + 1;
                } else {
                    right = middle - 1;
                }
            }
        }
        return -1;
    }
}
