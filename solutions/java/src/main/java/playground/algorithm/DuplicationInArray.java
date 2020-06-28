package playground.algorithm;

public class DuplicationInArray {

    public int findRepeatNumber(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (i == nums[i]) {
                continue;
            }
            if (nums[nums[i]] == nums[i]) {
                return nums[i];
            }
            int tmp = nums[i];
            nums[i] = nums[nums[i]];
            nums[tmp] = tmp;
        }
        return -1;
    }
}
