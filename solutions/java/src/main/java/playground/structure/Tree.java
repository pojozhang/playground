package playground.structure;

public interface Tree<T extends Comparable<T>> {

    Tree build(T[] ts);

    boolean isEmpty();

    boolean contains(T i);

    T findMin();

    T findMax();

    void insert(T t);

    void remove(T t);

    String graphTree();

    int deep();

    void show();

}
