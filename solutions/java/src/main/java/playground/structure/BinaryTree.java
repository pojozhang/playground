package playground.structure;

public class BinaryTree implements DemoTree<Integer> {

    public BinaryNode root;

    @Override
    public BinaryTree build(Integer[] integers) {
        root = new BinaryNode(integers[0]);
        for (Integer i : integers) {
            insert(i);
        }
        return this;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public boolean contains(Integer i) {
        return contains(i, root);
    }

    @Override
    public Integer findMin() {
        return findMin(root);
    }

    @Override
    public Integer findMax() {
        return finMax(root);
    }

    @Override
    public void insert(Integer integer) {
        insert(integer, root);
    }

    @Override
    public void remove(Integer integer) {
        remove(integer, root);
    }

    @Override
    public String printTree() {
        return print(root);
    }

    @Override
    public int deep() {
        return deep(root);
    }


    private boolean contains(Integer i, BinaryNode root) {
        if (root == null) return false;

        int compareResult = i.compareTo(root.e);

        if (compareResult < 0) return contains(i, root.l);

        else if (compareResult > 0) return contains(i, root.r);

        else return true;
    }

    private Integer findMin(BinaryNode node) {
        if (node == null) return null;
        if (node.l != null) return findMin(node.l);
        return node.e;
    }

    private Integer finMax(BinaryNode node) {
        if (isEmpty()) return null;

        BinaryNode t = node;
        while (t.r != null) {
            t = t.r;
        }

        return t.e;
    }

    private BinaryNode insert(Integer i, BinaryNode node) {
        if (node == null) return new BinaryNode(i);

        int compareResult = i.compareTo(node.e);
        if (compareResult < 0)
            node.l = insert(i, node.l);
        else if (compareResult > 0)
            node.r = insert(i, node.r);
//        else
//            ;
        return node;
    }

    private BinaryNode remove(Integer i, BinaryNode node) {
        if (node == null) return node;

        int compareResult = i.compareTo(node.e);
        if (compareResult < 0) {
            node.l = remove(i, node.l);
        } else if (compareResult > 0) {
            node.r = remove(i, node.r);
        } else {
            if (node.r != null && node.l != null) {
                // 要删除的两节都有值得情况
                node.r = remove(findMin(node.r), node.r);
            } else if (node.r != null || node.l != null) {
                // 只有一个子节点有值
                node = node.r == null ? node.l : node.r;
            } else {
                return null;
            }
        }
        return node;
    }

    private int deep(BinaryNode node) {
        if (node == null) return 0;
        int r = deep(node.r);
        int l = deep(node.l);
        return Math.max(r, l);
    }

    private String print(BinaryNode node) {
        if (node == null) return "";
        return "[ " + node.e + " l:(" + print(node.l) + ") r: (" + print(node.r) + " ]";
    }


    class BinaryNode {

        public BinaryNode(Integer e) {
            this(e, null, null);
        }

        public BinaryNode(Integer e, BinaryNode r, BinaryNode l) {
            this.e = e;
            this.r = r;
            this.l = l;
        }

        Integer e;

        BinaryNode r;

        BinaryNode l;

    }
}
