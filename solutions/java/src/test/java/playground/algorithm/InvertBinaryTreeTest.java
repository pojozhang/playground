package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvertBinaryTreeTest {

    private InvertBinaryTree solution = new InvertBinaryTree();

    @Test
    void case_1() {
        InvertBinaryTree.TreeNode root = new InvertBinaryTree.TreeNode(4);
        root.left = new InvertBinaryTree.TreeNode(2);
        root.right = new InvertBinaryTree.TreeNode(7);
        root.left.left = new InvertBinaryTree.TreeNode(1);
        root.left.right = new InvertBinaryTree.TreeNode(3);
        root.right.left = new InvertBinaryTree.TreeNode(6);
        root.right.right = new InvertBinaryTree.TreeNode(9);

        solution.invertTree(root);

        assertEquals(4, root.val);
        assertEquals(7, root.left.val);
        assertEquals(2, root.right.val);
        assertEquals(9, root.left.left.val);
        assertEquals(6, root.left.right.val);
        assertEquals(3, root.right.left.val);
        assertEquals(1, root.right.right.val);
    }
}