package playground.algorithm;

import org.junit.jupiter.api.Test;
import playground.algorithm.common.TreeNode;

import static org.assertj.core.api.Assertions.assertThat;

class CheckCompletenessOfABinaryTreeTest {

    private CheckCompletenessOfABinaryTree solution = new CheckCompletenessOfABinaryTree();

    @Test
    void case_1() {
        TreeNode root = TreeNode.of(1, 2, 3, 4, 5, 6);

        assertThat(solution.isCompleteTree(root)).isTrue();
    }

    @Test
    void case_2() {
        TreeNode root = TreeNode.of(1, 2, 3, 4, 5, null, 7);

        assertThat(solution.isCompleteTree(root)).isFalse();
    }
}