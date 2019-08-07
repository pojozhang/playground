package playground.algorithm.common;

public class TreeNode {

    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int x) {
        val = x;
    }

    public static TreeNode of(Integer... values) {
        TreeNode[] nodes = new TreeNode[values.length];
        for (int i = 0; i < values.length; i++) {
            if (values[i] == null) {
                continue;
            }

            TreeNode node = new TreeNode(values[i]);
            if (i > 0) {
                TreeNode parent = nodes[(i - 1) / 2];
                if (i % 2 == 0) {
                    parent.right = node;
                } else {
                    parent.left = node;
                }
            }
            nodes[i] = node;
        }
        return nodes[0];
    }
}
