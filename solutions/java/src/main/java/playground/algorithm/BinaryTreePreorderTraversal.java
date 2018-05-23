package playground.algorithm;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

/**
 * 前序遍历：根节点->左子树->右子树
 */
public class BinaryTreePreorderTraversal {

    static class TreeNode {

        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    // 迭代实现
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode node = root;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                result.add(node.val);
                stack.push(node);
                node = node.left;
            }

            /* 这里只要考虑两种情况
             *   1                  1
             *  /                  / \
             * 2 <- node  node -> 2   3
             *  \
             *   3
             */
            node = stack.pop().right;
        }
        return result;
    }

    // 递归实现
    public List<Integer> preorderTraversalRecursively(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }

        List<Integer> result = new ArrayList<>();
        result.add(root.val);
        result.addAll(preorderTraversalRecursively(root.left));
        result.addAll(preorderTraversalRecursively(root.right));
        return result;
    }
}
