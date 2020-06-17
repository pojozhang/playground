package playground.algorithm;

public class MinimumPathSum {

    public int minPathSum(int[][] grid) {
        int width = grid[0].length;
        int height = grid.length;
        int[][] dp = new int[height][width];
        for (int w = 0; w < width; w++) {
            dp[0][w] = (w > 0 ? dp[0][w - 1] : 0) + grid[0][w];
        }
        for (int h = 0; h < height; h++) {
            dp[h][0] = (h > 0 ? dp[h - 1][0] : 0) + grid[h][0];
        }

        for (int w = 1; w < width; w++) {
            for (int h = 1; h < height; h++) {
                dp[h][w] = Math.min(dp[h - 1][w], dp[h][w - 1]) + grid[h][w];
            }
        }

        return dp[height - 1][width - 1];
    }
}
