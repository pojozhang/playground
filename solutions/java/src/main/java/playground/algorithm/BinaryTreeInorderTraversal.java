package playground.algorithm;

import playground.algorithm.common.TreeNode;

import java.util.*;

/**
 * 中序遍历：左子树->根节点->右子树
 */
public class BinaryTreeInorderTraversal {

    // 迭代实现
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode node = root;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            result.add(node.val);
            node = node.right;
        }
        return result;
    }

    // 递归实现
    public List<Integer> inorderTraversalRecursively(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<Integer> list = new ArrayList<>(inorderTraversalRecursively(root.left));
        list.add(root.val);
        list.addAll(inorderTraversalRecursively(root.right));
        return list;
    }
}
