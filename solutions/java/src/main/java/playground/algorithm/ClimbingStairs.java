package playground.algorithm;

public class ClimbingStairs {

    public int climbStairs(int n) {
        if (n < 2) {
            return 1;
        }
        
        int f0 = 1, f1 = 1, f2 = 0;
        for (int i = 2; i <= n; i++) {
            f2 = f0 + f1;
            f0 = f1;
            f1 = f2;
        }
        return f2;
    }
}
