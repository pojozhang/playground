package playground.structure;

public class BinaryTree extends CommonBinaryTree<Integer>  {

    public BinaryNode<Integer> root;

    @Override
    public BinaryTree build(Integer[] integers) {
        root = new BinaryNode<Integer>(integers[0]);
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
    public String graphTree() {
        return graphTree(root);
    }

    @Override
    public int deep() {
        return deep(root);
    }

    private BinaryNode<Integer> insert(Integer i, BinaryNode<Integer> node) {
        if (node == null) return new BinaryNode<Integer>(i);

        int compareResult = i.compareTo(node.e);
        if (compareResult < 0)
            node.l = insert(i, node.l);
        else if (compareResult > 0)
            node.r = insert(i, node.r);
//        else
//            ;
        return node;
    }

    private BinaryNode<Integer> remove(Integer i, BinaryNode<Integer> node) {
        if (node == null) return null;

        int compareResult = i.compareTo(node.e);
        if (compareResult < 0) {
            node.l = remove(i, node.l);
        } else if (compareResult > 0) {
            node.r = remove(i, node.r);
        } else {
            if (node.r != null && node.l != null) { // 要删除的两节都有值得情况
                Integer min = findMin(node.r);
                node.e = min; // 找到当前节点的右侧分支的最小值，并赋值给当前节点元素
                node.r = remove(min, node.r); // 在右侧分支中删除最小值
            } else if (node.r != null || node.l != null) {
                // 只有一个子节点有值
                node = node.r == null ? node.l : node.r;
            } else {
                return null;
            }
        }
        return node;
    }

}
