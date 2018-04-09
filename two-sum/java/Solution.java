import java.util.Arrays;

public class Solution {
    
    public int[] twoSum(int[] nums, int target) {
        for (int i = 0 , length = nums.length ; i < length ; i ++ ){
            for (int j = i + 1 ; j < length ; j ++ ) {
                if (nums[i] + nums[j] == target) 
                    return new int[]{i , j};
            }
        }
        return new int[]{0 , 0};
    }

    public static void main(String[] args){
        Solution solution = new Solution();
        
        int[] nums = new int[]{2,1,7,20};
        int target = 9;

        assert Arrays.equals(new int[]{0,2}, solution.twoSum(nums, target));
        
        nums = new int[]{1,5,5,20};
        target = 10;

        assert Arrays.equals(new int[]{1,1}, solution.twoSum(nums, target));
        
        System.out.println("congratulation!");
    }
}
