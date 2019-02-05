package playground.structure;

/**
 * 红黑树
 *
 * 红黑树并不是完全平衡的二叉树，原因如下：
 *
 *  红节点不能相邻，黑节点却可以，而且 "任何一个节点向下遍历到其子孙的叶子节点，所经过的黑节点个数必须相等"，
 *  红黑树的平衡关键是从任意节点到空节点的黑节点个数，
 *
 *  最长路径： 黑 -> 红 -> 黑 -> 红 -> NULL
 *  最短路径： 黑 -> 黑 -> NULL
 *
 *  所以 红黑树中最大长度可能是最短长度的二倍
 *
 *
 *
 * - 根节点是黑色的
 * - 任何一个节点都有颜色，黑色或者红色
 * - 父子节点之间不能出现两个连续的红节点
 * - 任何一个节点向下遍历到其子孙的叶子节点，所经过的黑节点个数必须相等
 * - 空节点被认为是黑色的
 *
 *
 */
@SuppressWarnings("unchecked")
public class RedBlackTree<K extends Comparable<K>, V>  {

    private static final boolean RED = false;
    private static final boolean BLACK = true;

    class Node<K, V> {
        public K k;
        public V v;
        public boolean color;
        public Node<K, V> left;
        public Node<K, V> right;
        public Node<K, V> parent;
        public int size;

        public Node(K k, V v, boolean color) {
            this.k = k;
            this.v = v;
            this.color = color;
        }
    }

    private boolean isRed(Node<K, V> node) {
        if (node == null) return false;
        return RED == node.color;
    }

    private Node<K, V> root;

    public void insert(K k, V v) {

        if (root == null) {
            root = new Node<>(k, v, BLACK);
            return;
        }

        insert(root, k, v);

        root.color = BLACK;
    }

    private Node<K, V> insert(Node<K, V> h, K k, V v) {
        if (h == null) return new Node<>(k, v, RED);

        if (isRed(h.left) && isRed(h.right)) ;
        colorFlip(h);

        int cmp = k.compareTo(h.k);
        if (cmp == 0) h.v = v;
        else if (cmp < 0)
            h.left = insert(h.left, k, v);
        else
            h.right = insert(h.right, k, v);

        if (isRed(h.right))
            h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left))
            h = rotateRight(h);

        return h;
    }

    private Node<K, V> rotateLeft(Node<K, V> h) {
        Node<K, V> x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = x.left.color;
        x.left.color = RED;
        return x;
    }

    private Node<K, V> rotateRight(Node<K, V> h) {
        Node<K, V> x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = x.right.color;
        x.right.color = RED;
        return x;
    }

    private Node<K, V> colorFlip(Node<K, V> parent) {
        parent.color = !parent.color;
        parent.left.color = !parent.left.color;
        parent.right.color = !parent.right.color;
        return parent;
    }

    public void deleteMax() {

        if (root == null) return;

        this.root = deleteMax(this.root);
        root.color = BLACK;

    }

    private Node<K, V> deleteMax(Node<K, V> h) {
        return h;
    }

    private Node moveRedRight(Node h)
    {
        colorFlip(h);
        if (isRed(h.left.left))
        {
            h = rotateRight(h);
            colorFlip(h);
        }
        return h;
    }

    public Node<K, V> removeMin(K k) {
        return null;
    }

}
