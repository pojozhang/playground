package playground.algorithm;

public class UglyNumberII {

    public int nthUglyNumber(int n) {
        int[] uglyNumbers = new int[n];
        uglyNumbers[0] = 1;
        int ptr2 = 0, ptr3 = 0, ptr5 = 0;
        for (int i = 1; i < n; i++) {
            uglyNumbers[i] = Math.min(uglyNumbers[ptr2] * 2, Math.min(uglyNumbers[ptr3] * 3, uglyNumbers[ptr5] * 5));
            // 找到下一个x2后正好比当前丑数大的丑数。
            while (uglyNumbers[ptr2] * 2 <= uglyNumbers[i]) {
                ptr2++;
            }
            // 找到下一个x3后正好比当前丑数大的丑数。
            while (uglyNumbers[ptr3] * 3 <= uglyNumbers[i]) {
                ptr3++;
            }
            // 找到下一个x5后正好比当前丑数大的丑数。
            while (uglyNumbers[ptr5] * 5 <= uglyNumbers[i]) {
                ptr5++;
            }
        }
        return uglyNumbers[n - 1];
    }
}
