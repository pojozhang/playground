package playground.algorithm;

public class SingleNumber2 {

    public int singleNumber(int[] nums) {
        int result = 0;
        int[] bits = new int[32];

        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < bits.length; j++) {
                bits[j] += (nums[i] >> j) & 1;
            }
        }

        for (int i = 0; i < bits.length; i++) {
            if (bits[i] % 3 > 0) {
                result += 1 << i;
            }
        }

        return result;
    }
}
