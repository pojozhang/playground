package playground.algorithm;

import playground.algorithm.common.TreeNode;

public class BalancedBinaryTree {

    private static class Result {
        int depth;
        boolean isBalanced;

        public Result(int depth, boolean isBalanced) {
            this.depth = depth;
            this.isBalanced = isBalanced;
        }
    }

    public boolean isBalanced(TreeNode root) {
        return isBalancedRecursive(root).isBalanced;
    }

    // 后序遍历，自底向上。
    private Result isBalancedRecursive(TreeNode root) {
        if (root == null) {
            return new Result(0, true);
        }

        Result left = isBalancedRecursive(root.left);
        Result right = isBalancedRecursive(root.right);
        if (left.isBalanced && right.isBalanced && Math.abs(left.depth - right.depth) <= 1) {
            return new Result(Math.max(left.depth, right.depth) + 1, true);
        }
        return new Result(Math.max(left.depth, right.depth) + 1, false);
    }
}
