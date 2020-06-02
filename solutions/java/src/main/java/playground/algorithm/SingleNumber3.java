package playground.algorithm;

// 本题的关键是相同的数字异或的结果为0。
public class SingleNumber3 {

    public int[] singleNumber(int[] nums) {
        // 把数组中的所有数字进行异或操作。
        int xor = 0;
        for (int num : nums) {
            xor ^= num;
        }

        // 对于异或的结果找到其二进制表示的右起第一个1。
        int indexOfFirstOne = 0;
        while ((xor & 1) == 0) {
            xor >>= 1;
            indexOfFirstOne++;
        }

        // 根据数组中每个数字的二进制中的第indexOfFirstOne位是否是1分成2组分别进行异或操作。
        int[] result = new int[]{0, 0};
        for (int num : nums) {
            result[(num >> indexOfFirstOne & 1)] ^= num;
        }
        return result;
    }
}
