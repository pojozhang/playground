package playground.algorithm;

public class GreatestCommonDivisor {

    public int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
