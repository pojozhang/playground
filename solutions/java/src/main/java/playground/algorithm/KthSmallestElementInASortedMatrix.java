package playground.algorithm;

import java.util.PriorityQueue;

public class KthSmallestElementInASortedMatrix {

    public int kthSmallest(int[][] matrix, int k) {
        // 使用数组记录矩阵中每一个元素的值、所在的行、所在的列。
        // 例如{5, 0, 1}表示第0行第1列的元素是数字5。
        PriorityQueue<int[]> heap = new PriorityQueue<>(k, (o1, o2) -> o1[0] - o2[0]);
        for (int row = 0, column = 0; column < matrix.length; column++) {
            heap.offer(new int[]{matrix[row][column], row, column});
        }
        k--;
        while (k > 0) {
            // 弹出最小的元素。
            int[] peek = heap.poll();
            int row = peek[1];
            int column = peek[2];
            if (row < matrix.length - 1) {
                heap.offer(new int[]{matrix[row + 1][column], row + 1, column});
            }
            k--;
        }
        return heap.peek()[0];
    }
}
