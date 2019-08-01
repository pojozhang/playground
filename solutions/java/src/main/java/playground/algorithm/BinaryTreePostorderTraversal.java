package playground.algorithm;

import playground.algorithm.common.TreeNode;

import java.util.*;

/**
 * 后序遍历：左子树->右子树->根节点
 */
public class BinaryTreePostorderTraversal {

    // 迭代实现
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode node = root, lastVisited = null;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
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
            node = stack.peek();
            if (node.right == lastVisited) {
                result.add(stack.pop().val);
                lastVisited = node;
                node = null;
            } else {
                node = node.right;
                lastVisited = node;
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
