package playground.algorithm;

public class SpiralMatrix2 {

    public int[][] generateMatrix(int n) {
        int[][] matrix = new int[n][n];
        int up = 0, down = n - 1, left = 0, right = n - 1;
        int count = 0;
        while (count < n * n) {
            for (int l = left; l <= right; l++) {
                matrix[up][l] = ++count;
            }
            up++;

            for (int u = up; u <= down; u++) {
                matrix[u][right] = ++count;
            }
            right--;

            for (int r = right; r >= left; r--) {
                matrix[down][r] = ++count;
            }
            down--;

            for (int d = down; d >= up; d--) {
                matrix[d][left] = ++count;
            }
            left++;
        }
        return matrix;
    }
}
