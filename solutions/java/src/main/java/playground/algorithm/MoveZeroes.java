package playground.algorithm;

public class MoveZeroes {

    public void moveZeroes(int[] nums) {

        // 非零数 指针
        int num_no_zero = 0;
        int length = nums.length;

        // 递增非零指针，将所有的非零数添加至数组头
        for (int i = 0; i < length; i++) {
            if (nums[i] != 0) {
                nums[num_no_zero++] = nums[i];
            }
        }

        // 从非零指针之后添加数组零位
        for (int i = num_no_zero ; i < length ; i ++) {
            nums[i] = 0;
        }

    }
}
