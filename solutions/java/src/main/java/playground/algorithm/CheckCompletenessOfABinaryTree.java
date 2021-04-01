package playground.algorithm;

import playground.algorithm.common.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

public class CheckCompletenessOfABinaryTree {

    public boolean isCompleteTree(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        boolean foundNullNode = false;
        while (!queue.isEmpty()) {
            for (int i = 0; i < queue.size(); i++) {
                TreeNode node = queue.poll();
                if (node != null && !foundNullNode) {
                    queue.add(node.left);
                    queue.add(node.right);
                } else if (node != null && foundNullNode) {
                    return false;
                } else {
                    foundNullNode = true;
                }
            }
        }
        return true;
    }
}
