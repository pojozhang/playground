package playground.algorithm;

import org.junit.jupiter.api.Test;
import playground.algorithm.common.TreeNode;

import static org.assertj.core.api.Assertions.assertThat;

class LowestCommonAncestorOfABinaryTreeTest {

    private LowestCommonAncestorOfABinaryTree solution = new LowestCommonAncestorOfABinaryTree();

    @Test
    void case_1() {
        TreeNode node = solution.lowestCommonAncestor(TreeNode.of(3, 5, 1, 6, 2, 0, 8, null, null, 7, 4), TreeNode.of(5), TreeNode.of(1));

        assertThat(node.val).isEqualTo(3);
    }
}