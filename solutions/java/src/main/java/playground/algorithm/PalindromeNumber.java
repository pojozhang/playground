package playground.algorithm;

public class PalindromeNumber {
    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }

        int d = 1;
        /**
         * 以下写法存在益处问题，导致死循环
         * while ( d *10 <= x) {
         *   d *= 10;
         * }
         */
        while (x / d >= 10) {
            d *= 10;
        }

        int a = x, b = x;

        while (b > 0) {
            if (a / d != b % 10) {
                return false;
            }
            a %= d;
            d /= 10;
            b /= 10;
        }
        return true;
    }
}