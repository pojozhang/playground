package playground.structure;

public interface TreeInterface<T extends Comparable<T>> {

    TreeInterface build(T[] ts);

    boolean isEmpty();

    boolean contains(T i);

    T findMin();

    T findMax();

    void insert(T t);

    void remove(T t);

    String graphTree();

    void Show();

    int deep();

}
