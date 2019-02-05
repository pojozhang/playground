package playground.structure;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 二叉堆 - MIN : 计算机数据结构中最精致的一种
 *
 *             A
 *           /  \
 *          B    C
 *        /  \  / \
 *       D   E F  G
 * <p>
 * 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7
 *   | A | B | C | D | E | F | G
 *
 *
 * Futures
 * - 完美二叉树的节点组成形态
 * - 任意一个节点 i, 他的左子节点为 2 * i , 右节点为 2 * i + 1
 *   - (这是建立在一个 数组首位是不存放数据的数组中，如果数组首位也存放元素，则左节点为2 * i + 1 , 右节点为 2 * i+2 )
 * - 反向推倒，任意一个节点的父节点在 i / 2 位置上
 */
@SuppressWarnings("unchecked")
public class BinaryHeap<T extends Comparable<T>> {


    private static final int DEFAULT_CAPACITY = 10;

    // 当前堆中的元素个数
    private int currentSize;

    private T[] array;

    public BinaryHeap(int size, Class<T> type) {
        array = (T[]) Array.newInstance(type, size);
    }

    public BinaryHeap(T[] items) {

        int size = items.length;
        if (size < DEFAULT_CAPACITY) size = DEFAULT_CAPACITY;
        array = (T[]) Array.newInstance(items.getClass().getComponentType(), size);

        for (T item : items) {
            insert(item);
        }
    }

    public void insert(T item) {
        if (currentSize == array.length - 1) {
            array = Arrays.copyOf(array, array.length * 2 + 1);
        }

        // 上浮过滤
        int hole = ++currentSize;
        for (; hole > 1 && item.compareTo(array[hole / 2]) < 0; hole /= 2) {
            array[hole] = array[hole / 2];
        }

        array[hole] = item;
    }

    public T deleteMin() {

        T min = findMin();

        array[1] = array[currentSize--];

        percolateDown(1);

        return min;
    }

    public T findMin() {
        if (array != null && array.length > 1) return array[1];
        return null;
    }

    public boolean isEmpty() {
        return true;
    }

    private void percolateDown(int hole) {

        int child;

        T tmp = array[hole];

        for (; hole * 2 <= currentSize; hole = child) {

            child = hole * 2;

            // 找到当前hole的两个子节点中较小的一个
            if (child != currentSize && array[child + 1].compareTo(array[child]) < 0) {
                child++;
            }

            // 如果子节点小于当前节点，交换两个节点内数据
            if (array[child].compareTo(tmp) < 0) {
                array[hole] = array[child];
            } else {
                // 如果两个节点都比当前节点大，堆已经不需要再操作
                break;
            }
        }

        array[hole] = tmp;

    }

    public T[] getArray() {
        return Arrays.copyOfRange(array, 1, currentSize + 1);
    }
}
