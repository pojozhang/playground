package playground.algorithm;

public class MajorityElement {

    public int majorityElement(int[] nums) {
        return majorityElement(nums, 0, nums.length - 1);
    }

    private int majorityElement(int[] nums, int start, int end) {
        int key = nums[start];
        int l = start, r = end;
        while (l < r) {
            for (; l < r && nums[r] >= key; r--) ;
            for (; l < r && nums[l] <= key; l++) ;
            int tmp = nums[l];
            nums[l] = nums[r];
            nums[r] = tmp;
        }
        int tmp = nums[l];
        nums[l] = key;
        nums[start] = tmp;
        if (l > nums.length / 2) {
            return majorityElement(nums, start, l - 1);
        }
        if (l < nums.length / 2) {
            return majorityElement(nums, l + 1, end);
        }
        return nums[l];
    }
}
