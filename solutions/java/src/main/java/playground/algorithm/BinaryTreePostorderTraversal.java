package playground.algorithm;

import playground.algorithm.common.TreeNode;

import java.util.*;

/**
 * 后序遍历：左子树->右子树->根节点
 */
public class BinaryTreePostorderTraversal {

    // 迭代实现
    public List<Integer> postorderTraversal(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack1 = new LinkedList<>();
        Deque<TreeNode> stack2 = new LinkedList<>();
        stack1.push(root);
        while (!stack1.isEmpty()) {
            TreeNode node = stack1.pop();
            stack2.push(node);
            if (node.left != null) {
                stack1.push(node.left);
            }
            if (node.right != null) {
                stack1.push(node.right);
            }
        }
        while (!stack2.isEmpty()) {
            result.add(stack2.pop().val);
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
