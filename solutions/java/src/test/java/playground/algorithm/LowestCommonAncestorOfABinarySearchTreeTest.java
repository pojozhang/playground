package playground.algorithm;

import org.junit.jupiter.api.Test;
import playground.algorithm.common.TreeNode;

import static org.junit.jupiter.api.Assertions.*;

class LowestCommonAncestorOfABinarySearchTreeTest {

    private LowestCommonAncestorOfABinarySearchTree solution = new LowestCommonAncestorOfABinarySearchTree();

    @Test
    void case_1() {
        TreeNode root = TreeNode.of(6, 2, 8, 0, 4, 7, 9, null, null, 3, 5);

        TreeNode ancestor = solution.lowestCommonAncestor(root, new TreeNode(2), new TreeNode(8));

        assertEquals(6, ancestor.val);
    }

    @Test
    void case_2() {
        TreeNode root = TreeNode.of(6, 2, 8, 0, 4, 7, 9, null, null, 3, 5);

        TreeNode ancestor = solution.lowestCommonAncestor(root, new TreeNode(2), new TreeNode(4));

        assertEquals(2, ancestor.val);
    }

    @Test
    void case_3() {
        TreeNode root = TreeNode.of(2, 1);

        TreeNode ancestor = solution.lowestCommonAncestor(root, new TreeNode(2), new TreeNode(1));

        assertEquals(2, ancestor.val);
    }

    @Test
    void case_4() {
        TreeNode root = TreeNode.of(2, 1);

        TreeNode ancestor = solution.lowestCommonAncestor(root, new TreeNode(1), new TreeNode(2));

        assertEquals(2, ancestor.val);
    }

    @Test
    void case_5() {
        TreeNode root = TreeNode.of(6, 2, 8, 0, 4, 7, 9, null, null, 3, 5);

        TreeNode ancestor = solution.lowestCommonAncestor(root, new TreeNode(3), new TreeNode(5));

        assertEquals(4, ancestor.val);
    }
}