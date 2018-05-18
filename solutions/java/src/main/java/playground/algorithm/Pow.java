package playground.algorithm;

public class Pow {

    public double myPow(double x, int n) {
        if (x == 1) {
            return 1;
        }
        if (x == -1) {
            return n % 2 == 0 ? 1 : -1;
        }
        // Integer.MIN_VALUE 取反会溢出，因此要特殊处理
        if (n == Integer.MIN_VALUE) {
            return 0;
        }
        return n > 0 ? pow(x, n) : 1 / pow(x, -n);
    }

    private double pow(double x, int n) {
        if (n == 0) {
            return 1;
        }

        if (n == 1) {
            return x;
        }

        double value = myPow(x, n / 2);

        // 如果指数是偶数：x^2m = x^m * x^m
        // 如果指数是奇数：x^(2m+1) = x^m * x^m * x
        return value * value * (n % 2 == 0 ? 1 : x);
    }
}
