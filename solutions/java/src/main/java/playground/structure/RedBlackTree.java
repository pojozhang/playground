package playground.structure;

/**
 * 左倾 红黑树
 */
public class RedBlackTree implements Tree<Integer> {

    RBNode root;

    @Override
    public Tree build(Integer[] integers) {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Integer i) {
        return false;
    }

    @Override
    public Integer findMin() {
        return null;
    }

    @Override
    public Integer findMax() {
        return null;
    }

    @Override
    public void insert(Integer integer) {

    }

    @Override
    public void remove(Integer integer) {

    }

    @Override
    public String graphTree() {
        return null;
    }

    @Override
    public int deep() {
        return 0;
    }

    enum Color {
        RED, BLACK;
    }

    class RBNode {

        Color color;

        RBNode r;

        RBNode l;
    }
}
