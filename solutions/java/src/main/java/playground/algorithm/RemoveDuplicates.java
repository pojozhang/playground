package playground.algorithm;

public class RemoveDuplicates {

    public int removeDuplicates(int[] nums) {
        if (nums.length < 1) {
            return 0;
        }

        int size = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[size - 1]) {
                nums[size++] = nums[i];
            }
        }
        return size;
    }
}
