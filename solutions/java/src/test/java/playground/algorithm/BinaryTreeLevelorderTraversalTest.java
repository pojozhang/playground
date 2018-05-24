package playground.algorithm;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import playground.algorithm.BinaryTreeLevelorderTraversal.TreeNode;

class BinaryTreeLevelorderTraversalTest {

    private BinaryTreeLevelorderTraversal solution = new BinaryTreeLevelorderTraversal();

    /*
     *          1
     *         / \
     *        2   3
     *       /     \
     *      4       5
     *       \
     *        6
     *       / \
     *      7   8
     */
    @Test
    void case_1() {
        TreeNode head = new TreeNode(1);
        head.left = new TreeNode(2);
        head.right = new TreeNode(3);
        head.left.left = new TreeNode(4);
        head.right.right = new TreeNode(5);
        head.left.left.right = new TreeNode(6);
        head.left.left.right.left = new TreeNode(7);
        head.left.left.right.right = new TreeNode(8);

        assertIterableEquals(List.of(1, 2, 3, 4, 5, 6, 7, 8), solution.levelorderTraversal(head));
    }
}