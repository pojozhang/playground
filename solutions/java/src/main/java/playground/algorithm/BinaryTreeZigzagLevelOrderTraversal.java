package playground.algorithm;

import playground.algorithm.common.TreeNode;

import java.util.*;

public class BinaryTreeZigzagLevelOrderTraversal {

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<List<Integer>> orders = new LinkedList<>();
        Deque<TreeNode> deque = new LinkedList<>();
        deque.addFirst(root);
        int level = 0;
        while (!deque.isEmpty()) {
            int size = deque.size();
            List<Integer> order = new ArrayList<>(size);
            TreeNode node;
            for (int i = 0; i < size; i++) {
                if (level % 2 == 0) {
                    node = deque.pollFirst();
                    if (node.left != null) {
                        deque.addLast(node.left);
                    }
                    if (node.right != null) {
                        deque.addLast(node.right);
                    }
                } else {
                    node = deque.pollLast();
                    if (node.right != null) {
                        deque.addFirst(node.right);
                    }
                    if (node.left != null) {
                        deque.addFirst(node.left);
                    }
                }
                order.add(node.val);
            }
            level++;
            orders.add(order);
        }
        return orders;
    }
}
