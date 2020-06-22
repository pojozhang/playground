package playground.algorithm;

import playground.algorithm.common.TreeNode;

public class LowestCommonAncestorOfABinaryTree {

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        // 如果p、q分别位于左右子树，那么当前节点就是最近公共祖先。
        if (left != null && right != null) {
            return root;
        }
        // 如果当前节点是p或q则返回。
        if (root.val == p.val || root.val == q.val) {
            return root;
        }
        // 如果left不为空，那么说明left就是最近公共祖先。
        if (left != null) {
            return left;
        }
        // 如果right不为空，那么说明right就是最近公共祖先。
        if (right != null) {
            return right;
        }
        return null;
    }
}
