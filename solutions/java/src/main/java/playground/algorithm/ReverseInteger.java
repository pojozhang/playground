package playground.algorithm;

public class ReverseInteger {

    public int reverse(int x) {
        int n = x;
        long result = 0;
        while (n != 0) {
            result = result * 10 + n % 10;
            n /= 10;
        }

        if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
            return 0;
        }

        return (int) result;
    }
}