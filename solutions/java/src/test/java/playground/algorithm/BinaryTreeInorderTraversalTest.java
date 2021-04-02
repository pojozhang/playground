package playground.algorithm;

import org.junit.jupiter.api.Test;
import playground.algorithm.common.TreeNode;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class BinaryTreeInorderTraversalTest {

    private BinaryTreeInorderTraversal solution = new BinaryTreeInorderTraversal();

    /*
     * 1
     *  \
     *   2
     *  /
     * 3
     */
    @Test
    void case_1() {
        TreeNode head = new TreeNode(1);
        head.right = new TreeNode(2);
        head.right.left = new TreeNode(3);

        assertIterableEquals(List.of(1, 3, 2), solution.inorderTraversal(head));
        assertIterableEquals(List.of(1, 3, 2), solution.inorderTraversalRecursively(head));
    }

    /*
     *   3
     *  /
     * 1
     *  \
     *   2
     */
    @Test
    void case_2() {
        TreeNode head = TreeNode.of(3, 1, null, null, 2);

        assertIterableEquals(List.of(1, 2, 3), solution.inorderTraversal(head));
        assertIterableEquals(List.of(1, 2, 3), solution.inorderTraversalRecursively(head));
    }

    @Test
    void case_3() {
        assertIterableEquals(Collections.emptyList(), solution.inorderTraversal(null));
        assertIterableEquals(Collections.emptyList(), solution.inorderTraversalRecursively(null));
    }
}