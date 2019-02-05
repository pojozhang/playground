package playground.structure;

import playground.util.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Heap<T> {

    private final Comparator<T> comparator;
    private final List<T> elements;

    public Heap() {
        this(null);
    }

    public Heap(Comparator<T> comparator) {
        this.comparator = comparator;
        this.elements = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    public void addAll(T... entries) {
        for (T entry : entries) {
            add(entry);
        }
    }

    public void add(T entry) {
        this.elements.add(entry);
        int index = this.elements.size() - 1;
        while (index > 0 && compare(entry, this.elements.get((index - 1) / 2)) > 0) {
            Utils.swap(this.elements, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    public int size() {
        return this.elements.size();
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        return this.elements.isEmpty() ? null : this.elements.get(0);
    }

    @SuppressWarnings("unchecked")
    public T poll() {
        T entry = this.elements.isEmpty() ? null : this.elements.get(0);
        if (entry == null || this.elements.size() == 1) {
            return entry;
        }

        T last = this.elements.remove(this.elements.size() - 1);
        this.elements.set(0, last);
        heapify(0);
        return entry;
    }

    private void heapify(int index) {
        int previous = index, next;
        T current = this.elements.get(index);
        while (true) {
            int left = previous * 2 + 1;
            int right = previous * 2 + 2;
            if (left < this.elements.size() && compare(current, this.elements.get(left)) < 0) {
                next = left;
            } else if (right < this.elements.size() && compare(current, this.elements.get(right)) < 0) {
                next = right;
            } else {
                break;
            }
            Utils.swap(this.elements, previous, next);
            previous = next;
        }
    }

    @SuppressWarnings("unchecked")
    private int compare(T first, T second) {
        if (comparator != null) {
            return comparator.compare(first, second);
        }
        return ((Comparable) first).compareTo(second);
    }
}
