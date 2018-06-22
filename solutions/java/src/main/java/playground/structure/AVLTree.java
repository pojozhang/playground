package playground.structure;

/**
 * AVL Tree (Adelson-Velskii And Landis)
 * <p>
 * 每个结点的左子树和右子树的高度差最多差1的二叉查找树
 */
public class AVLTree extends CommonBinaryTree<Integer> {

    private BinaryNode<Integer> root;

    @Override
    public Tree build(Integer[] integers) {
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
    public String graphTree() {
        return graphTree(root);
    }

    @Override
    public int deep() {
        return deep(root);
    }

    @Override
    public void insert(Integer integer) {

    }

    @Override
    public void remove(Integer integer) {

    }

}
