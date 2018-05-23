package playground.algorithm;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

/**
 * 后序遍历：左子树->右子树->根节点
 */
public class BinaryTreePostorderTraversal {

    static class TreeNode {

        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    // 迭代实现
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode node = root, lastVisit = null;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            node = stack.peek();

            // 无右子树
            if (node.right == null) {
                result.add(stack.pop().val);
                lastVisit = node;
                node = null;
            } else {
                node = node.right;
            }
        }

        return result;
    }

    // 递归实现
    public List<Integer> postorderTraversalRecursively(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }

        List<Integer> result = new ArrayList<>(postorderTraversalRecursively(root.left));
        result.addAll(postorderTraversalRecursively(root.right));
        result.add(root.val);
        return result;
    }
}
