package playground.algorithm;

import java.util.Comparator;
import java.util.PriorityQueue;

public class MedianFinder {

    private PriorityQueue<Integer> low;
    private PriorityQueue<Integer> high;

    public MedianFinder() {
        low = new PriorityQueue<>(Comparator.reverseOrder());
        high = new PriorityQueue<>();
    }

    public void addNum(int num) {
        if (low.isEmpty()) {
            low.add(num);
            return;
        }
        if (low.size() > high.size()) {
            if (num < low.peek()) {
                high.add(low.poll());
                low.add(num);
            } else {
                high.add(num);
            }
        } else if (low.size() < high.size()) {
            if (num > high.peek()) {
                low.add(high.poll());
                high.add(num);
            } else {
                low.add(num);
            }
        } else {
            if (num < high.peek()) {
                low.add(num);
            } else {
                high.add(num);
            }
        }
    }

    public double findMedian() {
        if (high.isEmpty()) {
            return low.peek();
        }
        return (low.size() + high.size()) % 2 == 0 ?
                (low.peek() + high.peek()) / 2d : (low.size() > high.size() ? low.peek() : high.peek());
    }
}
