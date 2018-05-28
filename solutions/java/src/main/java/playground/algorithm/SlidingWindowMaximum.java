package playground.algorithm;

import java.util.*;

public class SlidingWindowMaximum {

    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums.length < 1) {
            return new int[0];
        }

        List<Integer> result = new ArrayList<>();
        Deque<Integer> deque = new ArrayDeque<>();
        // 大小为k的窗口
        for (int i = 0; i < k; i++) {
            while (!deque.isEmpty() && nums[i] >= nums[deque.getLast()]) {
                deque.pollLast();
            }
            deque.addLast(i);
        }

        // 窗口向后滑动
        for (int i = k; i < nums.length; i++) {
            result.add(nums[deque.getFirst()]);
            // 把超出窗口范围的元素移出队列
            if (deque.getFirst() <= i - k) {
                deque.pollFirst();
            }
            while (!deque.isEmpty() && nums[i] >= nums[deque.getLast()]) {
                deque.pollLast();
            }
            deque.addLast(i);
        }

        result.add(nums[deque.getFirst()]);

        return result.stream().mapToInt(Integer::intValue).toArray();
    }
}
