package playground.algorithm;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import playground.algorithm.BinaryTreeInorderTraversal.TreeNode;

class BinaryTreeInorderTraversalTest {

    private BinaryTreeInorderTraversal solution = new BinaryTreeInorderTraversal();

    @Test
    void case_1() {
        TreeNode head = new TreeNode(1);
        head.right = new TreeNode(2);
        head.right.left = new TreeNode(3);

        assertIterableEquals(List.of(1, 3, 2), solution.inorderTraversal(head));
        assertIterableEquals(List.of(1, 3, 2), solution.inorderTraversalRecursively(head));
    }

    @Test
    void case_2() {
        assertIterableEquals(Collections.emptyList(), solution.inorderTraversal(null));
        assertIterableEquals(Collections.emptyList(), solution.inorderTraversalRecursively(null));
    }
}