package playground.algorithm;

import java.util.PriorityQueue;

public class KthSmallestElementInASortedMatrix {

    public int kthSmallest(int[][] matrix, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>(k, (o1, o2) -> o2 - o1);

        for (int row = 0; row < matrix.length; row++) {
            for (int column = 0; column < matrix[row].length; column++) {
                if (heap.size() < k) {
                    heap.add(matrix[row][column]);
                } else if (matrix[row][column] <= heap.peek()) {
                    heap.poll();
                    heap.add(matrix[row][column]);
                } else {
                    break;
                }
            }
        }

        return heap.peek();
    }
}
