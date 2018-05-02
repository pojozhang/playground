package playground.algorithm;

public class RemoveElement {

    public int removeElement(int[] nums, int val) {
        int i = 0, len = nums.length;
        while (i < len) {
            if (nums[i] == val) {
                nums[i] = nums[--len];
                continue;
            }
            i++;
        }
        return len;
    }
}
