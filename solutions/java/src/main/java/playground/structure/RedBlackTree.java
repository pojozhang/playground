package playground.structure;

/**
 * 左倾 红黑树
 * <p>
 * - 两根红色节点不能连在一起
 * -
 */
public class RedBlackTree<K extends Comparable, V> {

    Node root = new Node();

    public V get(K k) {
        Node x = root;
        while (x != null) {
            int cmp = k.compareTo(x.k);
            if (cmp == 0) return x.v;
            else if (cmp < 0) x = x.l;
            else if (cmp > 0) x = x.r;
        }
        return null;
    }


    public void set(K k, V v) {

    }

    class Node {

        K k;

        V v;

        Color color;

        Node r;

        Node l;

    }

    enum Color {
        RED, BLACK;
    }
}
