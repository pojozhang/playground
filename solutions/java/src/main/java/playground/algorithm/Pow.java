package playground.algorithm;

public class Pow {

    public double myPow(double x, int n) {
        //此处如果n<0不需要去反，如果取反会有溢出的可能（负数范围比正数大）
        //如果取反，那么还要针对 n = Integer.MIN_VALUE 进行特殊处理，多此一举
        return n >= 0 ? pow(x, n) : 1 / pow(x, n);
    }

    private double pow(double x, int n) {
        if (n == 0) {
            return 1;
        }

        if (n == 1) {
            return x;
        }

        double value = pow(x, n / 2);

        // 如果指数是偶数：x^2m = x^m * x^m
        // 如果指数是奇数：x^(2m+1) = x^m * x^m * x
        return value * value * (n % 2 == 0 ? 1 : x);
    }
}
