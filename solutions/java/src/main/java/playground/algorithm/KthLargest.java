package playground.algorithm;

import java.util.PriorityQueue;

public class KthLargest {

    private final int k;
    private final PriorityQueue<Integer> priorityQueue;

    public KthLargest(int k, int[] nums) {
        this.k = k;
        this.priorityQueue = new PriorityQueue<>();
        for (int i = 0; i < nums.length; i++) {
            add(nums[i]);
        }
    }

    public int add(int val) {
        if (priorityQueue.size() < k) {
            priorityQueue.add(val);
        } else if (priorityQueue.peek() < val) {
            priorityQueue.poll();
            priorityQueue.add(val);
        }
        return priorityQueue.peek();
    }
}
