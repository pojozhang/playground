package playground.algorithm;

public class FibonacciNumber {

    public int fib(int n) {
        if (n < 2) {
            return n;
        }
        int a = 0, b = 1;
        int sum = 0;
        for (int i = 2; i <= n; i++) {
            sum = a + b;
            a = b;
            b = sum;
        }
        return sum;
    }
}
