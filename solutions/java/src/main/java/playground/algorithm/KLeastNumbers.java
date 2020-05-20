package playground.algorithm;

import java.util.Comparator;
import java.util.PriorityQueue;

public class KLeastNumbers {

    public int[] getLeastNumbers(int[] array, int k) {
        if (k < 1) {
            return new int[0];
        }
        PriorityQueue<Integer> heap = new PriorityQueue<>(k, Comparator.reverseOrder());
        for (int i = 0; i < k; i++) {
            heap.add(array[i]);
        }
        for (int i = k; i < array.length; i++) {
            if (heap.peek() > array[i]) {
                heap.poll();
                heap.add(array[i]);
            }
        }
        int[] result = new int[k];
        int i = 0;
        while (!heap.isEmpty()) {
            result[i++] = heap.poll();
        }
        return result;
    }
}
