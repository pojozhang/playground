package playground.algorithm;

public class FirstMissingPositive {

    public int firstMissingPositive(int[] nums) {
        int temp;
        for (int i = 0; i < nums.length; ) {
            if (nums[i] - 1 < 0 || nums[i] - 1 >= nums.length || nums[nums[i] - 1] == nums[i]) {
                i++;
                continue;
            }
            temp = nums[nums[i] - 1];
            nums[nums[i] - 1] = nums[i];
            nums[i] = temp;
        }

        for (int j = 0; j < nums.length; j++) {
            if (nums[j] != j + 1) {
                return j + 1;
            }
        }

        return nums.length + 1;
    }
}
