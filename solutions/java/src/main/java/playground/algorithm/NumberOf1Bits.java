package playground.algorithm;

public class NumberOf1Bits {

    public int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            //消除n的二进制表示中最右边的1
            n &= n - 1;
            count++;
        }
        return count;
    }
}
