package playground.algorithm;

public class NumberOfDigitOne {

    public int countDigitOne(int n) {
        if (n <= 0) {
            return 0;
        }
        int count = 0;
        int length = getLength(n);
        int pow = (int) Math.pow(10, length - 1);
        // 计算数字的最高位。
        int firstDigit = n / pow;
        // 如果数字的最高位大于1，那么该位数贡献了pow个1，比如5234中的最高位5贡献了10000-19999个共10000个1。
        if (firstDigit > 1) {
            count += pow;
        } else {
            // 如果数字的最高位等于1，那么该位数贡献了n - pow + 1个1，比如1234中的最高位1贡献了1234-1000+1个1，也就是235个1。
            count += n - pow + 1;
        }
        // firstDigit * (length - 1) * Math.pow(10, length - 2)部分是计算除最高位以外的其他位上的1的个数。
        count += firstDigit * (length - 1) * Math.pow(10, length - 2) + countDigitOne(n % pow);
        return count;
    }

    private int getLength(int n) {
        int length = 1;
        while ((n /= 10) > 0) {
            length++;
        }
        return length;
    }
}
