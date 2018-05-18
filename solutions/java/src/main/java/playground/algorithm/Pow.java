package playground.algorithm;

public class Pow {

    public double myPow(double x, int n) {
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

        // 如果指数是偶数：x^2n = x^n * x^n
        // 如果指数是奇数：x^(2n+1) = x^n * x^n +1
        return value * value * (n % 2 == 0 ? 1 : x);
    }
}
