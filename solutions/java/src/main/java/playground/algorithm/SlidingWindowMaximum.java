package playground.algorithm;

import java.util.Deque;
import java.util.LinkedList;

public class SlidingWindowMaximum {

    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums.length < 1) {
            return new int[0];
        }
        if (k == 1) {
            return nums;
        }
        int[] result = new int[nums.length - k + 1];
        Deque<Integer> deque = new LinkedList<>();
        for (int i = 0; i < nums.length; i++) {
            if (deque.isEmpty()) {
                deque.addFirst(i);
                continue;
            }
            if (i - deque.getFirst() >= k) {
                deque.pollFirst();
            }
            while (!deque.isEmpty() && nums[i] >= nums[deque.getLast()]) {
                deque.pollLast();
            }
            deque.addLast(i);
            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.getFirst()];
            }
        }
        return result;
    }
}
