package playground.algorithm;

import org.junit.jupiter.api.Test;
import playground.algorithm.common.TreeNode;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BinaryTreeZigzagLevelOrderTraversalTest {

    private BinaryTreeZigzagLevelOrderTraversal solution = new BinaryTreeZigzagLevelOrderTraversal();

    @Test
    void case_1() {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);

        List<List<Integer>> result = solution.zigzagLevelOrder(root);

        assertIterableEquals(List.of(
                List.of(3),
                List.of(20, 9),
                List.of(15, 7)), result);
    }

    @Test
    void case_2() {
        TreeNode root = new TreeNode(1);

        List<List<Integer>> result = solution.zigzagLevelOrder(root);

        assertIterableEquals(List.of(List.of(1)), result);
    }

    @Test
    void case_3() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(3);
        root.left.left.left = new TreeNode(4);
        root.left.left.left.left = new TreeNode(5);

        List<List<Integer>> result = solution.zigzagLevelOrder(root);

        assertIterableEquals(List.of(
                List.of(1),
                List.of(2),
                List.of(3),
                List.of(4),
                List.of(5)), result);
    }
}