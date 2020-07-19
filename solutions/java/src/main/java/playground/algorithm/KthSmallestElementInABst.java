package playground.algorithm;

import playground.algorithm.common.TreeNode;

import java.util.Objects;

public class KthSmallestElementInABst {

    private int k;

    public int kthSmallest(TreeNode root, int k) {
        this.k = k;
        return Objects.requireNonNull(kthSmallestNode(root)).val;
    }

    private TreeNode kthSmallestNode(TreeNode node) {
        if (node.left != null) {
            TreeNode result = kthSmallestNode(node.left);
            if (result != null) {
                return result;
            }
            k--;
        }

        if (k == 1) {
            return node;
        }

        if (node.right != null) {
            k--;
            TreeNode result = kthSmallestNode(node.right);
            if (result != null) {
                return result;
            }
        }
        return null;
    }
}