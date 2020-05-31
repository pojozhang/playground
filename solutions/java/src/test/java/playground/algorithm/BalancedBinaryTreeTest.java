package playground.algorithm;

import org.junit.jupiter.api.Test;
import playground.algorithm.common.TreeNode;

import static org.assertj.core.api.Assertions.assertThat;

class BalancedBinaryTreeTest {

    private BalancedBinaryTree solution = new BalancedBinaryTree();

    @Test
    void case_1() {
        assertThat(solution.isBalanced(TreeNode.of(3, 9, 20, null, null, 15, 7))).isTrue();
    }

    @Test
    void case_2() {
        assertThat(solution.isBalanced(TreeNode.of(1, 2, 2, 3, 3, null, null, 4, 4))).isFalse();
    }
}