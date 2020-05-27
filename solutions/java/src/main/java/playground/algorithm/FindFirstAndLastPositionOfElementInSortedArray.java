package playground.algorithm;

public class FindFirstAndLastPositionOfElementInSortedArray {

    public int[] searchRange(int[] nums, int target) {
        int start = findFirst(nums, 0, nums.length - 1, target);
        int end = findLast(nums, 0, nums.length - 1, target);
        return new int[]{start, end};
    }

    private int findFirst(int[] nums, int start, int end, int target) {
        while (start <= end) {
            int mid = (start + end) / 2;
            if (nums[mid] > target) {
                end = mid - 1;
            } else if (nums[mid] < target) {
                start = mid + 1;
            } else {
                if (mid == start || mid > start && nums[mid - 1] < target) {
                    return mid;
                }
                end = mid - 1;
            }
        }
        return -1;
    }

    private int findLast(int[] nums, int start, int end, int target) {
        while (start <= end) {
            int mid = (start + end) / 2;
            if (nums[mid] > target) {
                end = mid - 1;
            } else if (nums[mid] < target) {
                start = mid + 1;
            } else {
                if (mid == end || mid < end && nums[mid + 1] > target) {
                    return mid;
                }
                start = mid + 1;
            }
        }
        return -1;
    }
}
