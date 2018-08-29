package playground.structure;

/**
 * 左倾 红黑树
 *
 * - 任何一个节点都有颜色，黑色或者红色
 * - 根节点是黑色的
 * - 父子节点之间不能出现两个连续的红节点
 * - 任何一个节点向下遍历到其子孙的叶子节点，所经过的黑节点个数必须相等
 * - 空节点被认为是黑色的
 */
public class RedBlackTree implements Tree<Integer> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    RBNode<Integer> root;

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

        // 红黑树 根节点为黑色
        if (root == null) {
            root = new RBNode<>(integer, null, BLACK);
        } else {

            RBNode<Integer> parent = null;
            RBNode<Integer> node = this.root;

            // 1. 将红黑树当作一颗二叉查找树，将节点添加到二叉查找树中。
            while (node != null) {
                parent = node;
                int cmp = integer.compareTo(node.value);
                if (cmp < 0)
                    node = node.left;
                else
                    node = node.right;
            }

            // 2. 新建一个红色的节点
            RBNode<Integer> newNode = new RBNode<>(integer, parent, RED);

            // 3. 连接到父节点
            int cmp = integer.compareTo(parent.value);
            if (cmp < 0)
                parent.left = newNode;
            else if (cmp > 0)
                parent.right = newNode;
//            else
//                ;

            // 4. 将它重新修正为一颗二叉查找树
            insertFix(newNode);
        }
    }

    /**
     * 插入修复
     *
     * - 插入节点为红色，父节点为黑色，并且与祖父节点的同侧
     * - 插入节点为红色，
     * @param node
     */
    private void insertFix(RBNode<Integer> node) {

        RBNode<Integer> parent, gparent;

        // 若 “父节点存在，并且父节点的颜色是红色”
        while (((parent = node.parent) != null) && parent.color) {
            gparent = parent.parent;

            // 若 “父节点” 是 “祖父节点的左孩子”
            if (parent == gparent.left) {

                // Case 1条件：叔叔节点是红色
                RBNode<Integer> uncle = gparent.right;

                if ((uncle != null) && isRed(uncle)) {
                    uncle.color = BLACK;
                    parent.color = BLACK;
                    gparent.color = RED;
                    node = gparent;
                    continue;
                }

                // Case 2条件：叔叔是黑色，且当前节点是右孩子
                if (parent.right == node) {
                    RBNode<Integer> tmp;
                    leftRotate(parent);
                    tmp = parent;
                    parent = node;
                    node = tmp;
                }

                // Case 3条件：叔叔是黑色，且当前节点是左孩子。
                parent.color = BLACK;
                gparent.color = RED;
                rightRotate(gparent);

            } else { //若“z的父节点”是“z的祖父节点的右孩子”

                // Case 1条件：叔叔节点是红色
                RBNode<Integer> uncle = gparent.left;
                if ((uncle!=null) && isRed(uncle)) {
                    uncle.color = BLACK;
                    parent.color = BLACK;
                    gparent.color = RED;
                    node = gparent;
                    continue;
                }

                // Case 2条件：叔叔是黑色，且当前节点是左孩子
                if (parent.left == node) {
                    RBNode<Integer> tmp;
                    rightRotate(parent);
                    tmp = parent;
                    parent = node;
                    node = tmp;
                }

                // Case 3条件：叔叔是黑色，且当前节点是右孩子。
                parent.color = BLACK;
                gparent.color = RED;
                leftRotate(gparent);
            }
        }

        // 将根节点设为黑色
        this.root.color = BLACK;
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

    @Override
    public void show() {

    }

    /**
     * 对红黑树的节点(x)进行左旋转
     *
     * 左旋示意图(对节点x进行左旋)：
     *      px                              px
     *     /                               /
     *    x                               y
     *   /  \      --(左旋)-.           / \                #
     *  lx   y                          x  ry
     *     /   \                       /  \
     *    ly   ry                     lx  ly
     *
     *
     */
    private void leftRotate(RBNode x) {
        // 设置x的右孩子为y
        RBNode y = x.right;

        // 将 “y的左孩子” 设为 “x的右孩子”；
        // 如果y的左孩子非空，将 “x” 设为 “y的左孩子的父亲”
        x.right = y.left;
        if (y.left != null)
            y.left.parent = x;

        // 将 “x的父亲” 设为 “y的父亲”
        y.parent = x.parent;

        if (x.parent == null) {
            this.root = y;            // 如果 “x的父亲” 是空节点，则将y设为根节点
        } else {
            if (x.parent.left == x)
                x.parent.left = y;    // 如果 x是它父节点的左孩子，则将y设为“x的父节点的左孩子”
            else
                x.parent.right = y;    // 如果 x是它父节点的左孩子，则将y设为“x的父节点的左孩子”
        }

        // 将 “x” 设为 “y的左孩子”
        y.left = x;
        // 将 “x的父节点” 设为 “y”
        x.parent = y;
    }

    /**
     * 对红黑树的节点(y)进行右旋转
     *
     * 右旋示意图(对节点y进行左旋)：
     *            py                               py
     *           /                                /
     *          y                                x
     *         /  \      --(右旋)-.            /  \                     #
     *        x   ry                           lx   y
     *       / \                                   / \                   #
     *      lx  rx                                rx  ry
     *
     */
    private void rightRotate(RBNode y) {
        // 设置x是当前节点的左孩子。
        RBNode x = y.left;

        // 将 “x的右孩子” 设为 “y的左孩子”；
        // 如果"x的右孩子"不为空的话，将 “y” 设为 “x的右孩子的父亲”
        y.left = x.right;
        if (x.right != null)
            x.right.parent = y;

        // 将 “y的父亲” 设为 “x的父亲”
        x.parent = y.parent;

        if (y.parent == null) {
            this.root = x;            // 如果 “y的父亲” 是空节点，则将x设为根节点
        } else {
            if (y == y.parent.right)
                y.parent.right = x;    // 如果 y是它父节点的右孩子，则将x设为“y的父节点的右孩子”
            else
                y.parent.left = x;    // (y是它父节点的左孩子) 将x设为“x的父节点的左孩子”
        }

        // 将 “y” 设为 “x的右孩子”
        x.right = y;

        // 将 “y的父节点” 设为 “x”
        y.parent = x;
    }

    private boolean isRed(RBNode<Integer> node){
        return node.color;
    }

    private RBNode<Integer> parentOf(RBNode<Integer> node) {
        return node.parent;
    }

    class RBNode<T extends Comparable<T>> {

        public T value;
        public RBNode<T> parent;
        public boolean color;
        public RBNode<T> left;
        public RBNode<T> right;


        RBNode(T value, RBNode<T> parent) {
            this(value, parent, true);
        }

        RBNode(T value, RBNode<T> parent, boolean color) {
            this.value = value;
            this.parent = parent;
            this.color = color;
        }
    }
}
