package playground.algorithm;

import org.junit.jupiter.api.Test;
import playground.algorithm.common.TreeNode;

import static org.junit.jupiter.api.Assertions.*;

class SubtreeOfAnotherTreeTest {

    private SubtreeOfAnotherTree solution = new SubtreeOfAnotherTree();

    @Test
    void case_1() {
        assertTrue(solution.isSubtree(TreeNode.of(3, 4, 5, 1, 2), TreeNode.of(4, 1, 2)));
    }

    @Test
    void case_2() {
        assertFalse(solution.isSubtree(TreeNode.of(3, 4, 5, 1, null, 2), TreeNode.of(3, 1, 2)));
    }

    @Test
    void case_3() {
        assertFalse(solution.isSubtree(TreeNode.of(3, 4, 5, 1, 2, null, null, 0), TreeNode.of(4, 1, 2)));
    }
}