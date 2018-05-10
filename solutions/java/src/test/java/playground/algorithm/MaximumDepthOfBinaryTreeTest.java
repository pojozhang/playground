package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MaximumDepthOfBinaryTreeTest {

    private MaximumDepthOfBinaryTree solution = new MaximumDepthOfBinaryTree();

    @Test
    void case_1() {
        MaximumDepthOfBinaryTree.TreeNode root = new MaximumDepthOfBinaryTree.TreeNode(3);
        root.left = new MaximumDepthOfBinaryTree.TreeNode(9);
        root.right = new MaximumDepthOfBinaryTree.TreeNode(20);
        root.right.left = new MaximumDepthOfBinaryTree.TreeNode(15);
        root.right.right = new MaximumDepthOfBinaryTree.TreeNode(7);

        assertEquals(3, solution.maxDepth(root));
    }
}