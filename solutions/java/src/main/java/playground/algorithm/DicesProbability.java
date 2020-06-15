package playground.algorithm;

public class DicesProbability {

    public double[] twoSum(int n) {
        // dp[i][j]表示i个骰子和为j出现的次数。
        int[][] dp = new int[n + 1][6 * n + 1];
        // 边界处理。
        for (int i = 1; i <= 6; i++) {
            dp[1][i] = 1;
        }
        for (int i = 2; i <= n; i++) {
            for (int j = i; j <= 6 * i; j++) {
                for (int m = j - 1; m >= 1 && m >= j - 6; m--) {
                    // 状态转移方程：投掷完i枚骰子后点数j出现的次数，可以由投掷完i-1枚骰子后，对应点数j-1, j-2, j-3, ... , j-6出现的次数之和转化过来。
                    dp[i][j] += dp[i - 1][m];
                }
            }
        }
        double total = Math.pow(6, n);
        double[] result = new double[6 * n - (n - 1)];
        for (int i = n; i <= 6 * n; i++) {
            result[i - n] = dp[n][i] / total;
        }
        return result;
    }
}
