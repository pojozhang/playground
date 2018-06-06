package playground.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * @see BinaryHeap
 */
public class BinaryHeapInList<T extends Comparable<T>> {

    private static final int default_size = 10;

    // 当前堆中的元素个数
    private int currentSize;

    private List<T> array;

    public BinaryHeapInList() {
        array = new ArrayList<>(default_size);
        set(0, null);
    }

    public BinaryHeapInList(T[] items) {
        this();
        for (T item : items) {
            insert(item);
        }
    }

    public void insert(T item) {
        // 上浮过滤
        int hole = ++currentSize;
        for (; hole > 1 && item.compareTo(array.get(hole / 2)) < 0; hole /= 2) {
            set(hole, array.get(hole / 2));
        }
        set(hole, item);
    }

    public T deleteMin() {

        T min = findMin();

        set(1, array.get(currentSize--));

        percolateDown(1);

        return min;
    }

    public T findMin() {
        if (currentSize > 0) return array.get(1);
        return null;
    }

    public boolean isEmpty() {
        return currentSize <= 0;
    }

    private void percolateDown(int hole) {

        int child;

        T tmp = array.get(hole);

        for (; hole * 2 <= currentSize; hole = child) {

            child = hole * 2;

            // 找到当前hole的两个子节点中较小的一个
            if (child != currentSize
                    && array.get(child + 1).compareTo(array.get(child)) < 0) {
                child++;
            }

            // 如果子节点小于当前节点，交换两个节点内数据
            if (array.get(child).compareTo(tmp) < 0) {
                set(hole, array.get(child));
            } else {
                // 如果两个节点都比当前节点大，堆已经不需要再操作
                break;
            }
        }

        set(hole, tmp);
    }

    private void set(int index, T item) {
        if (index >= array.size()) {
            array.add(item);
        } else {
            array.set(index, item);
        }
    }

    public List<T> getArray() {
        List<T> result = new ArrayList<>();
        for (int i = 1; i < currentSize; i++) {
            result.add(array.get(i));
        }

        return result;
    }
}
