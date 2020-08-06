package playground.algorithm;

public class RobotMove {

    public int movingCount(int m, int n, int k) {
        boolean[][] flag = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                flag[i][j] = false;
            }
        }
        return movingCount(m, n, k, 0, 0, flag);
    }

    private int movingCount(int m, int n, int k, int x, int y, boolean[][] flag) {
        if (x < 0 || y < 0 || x >= m || y >= n || flag[x][y] || bitSum(x, y) > k) {
            return 0;
        }
        flag[x][y] = true;
        int count = 1;
        count += movingCount(m, n, k, x - 1, y, flag);
        count += movingCount(m, n, k, x + 1, y, flag);
        count += movingCount(m, n, k, x, y - 1, flag);
        count += movingCount(m, n, k, x, y + 1, flag);
        return count;
    }

    private int bitSum(int x, int y) {
        int sum = 0;
        while (x > 0 || y > 0) {
            sum += x % 10 + y % 10;
            x /= 10;
            y /= 10;
        }
        return sum;
    }
}
