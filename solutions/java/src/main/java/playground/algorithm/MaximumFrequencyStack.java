package playground.algorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class MaximumFrequencyStack {

    private PriorityQueue<Entry> queue;
    private Map<Integer, Integer> frequencies;
    private int sequence = 0;

    public MaximumFrequencyStack() {
        this.frequencies = new HashMap<>();
        this.queue = new PriorityQueue<>((o1, o2) -> {
            if (o1.frequency == o2.frequency) {
                return o2.sequence - o1.sequence;
            }
            return o2.frequency - o1.frequency;
        });
    }

    public void push(int x) {
        Integer frequency = frequencies.getOrDefault(x, 0) + 1;
        this.queue.add(new Entry(x, frequency));
        frequencies.put(x, frequency);
    }

    public int pop() {
        Entry peek = this.queue.poll();
        this.frequencies.put(peek.value, this.frequencies.get(peek.value) - 1);
        return peek.value;
    }

    private class Entry {
        int value;
        int frequency;
        int sequence;

        public Entry(int value, int frequency) {
            this.value = value;
            this.frequency = frequency;
            this.sequence = MaximumFrequencyStack.this.sequence++;
        }
    }
}
