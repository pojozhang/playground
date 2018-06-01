package playground.structure;

public interface DemoTree<T extends Comparable<T>> {

    DemoTree build(T[] ts);

    boolean isEmpty();

    boolean contains(T i);

    T findMin();

    T findMax();

    void insert(T t);

    void remove(T t);

    String printTree();

    int deep();

}
