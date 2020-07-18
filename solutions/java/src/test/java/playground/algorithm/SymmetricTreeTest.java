package playground.algorithm;

import org.junit.jupiter.api.Test;
import playground.algorithm.common.TreeNode;

import static org.assertj.core.api.Assertions.assertThat;

class SymmetricTreeTest {

    private SymmetricTree solution = new SymmetricTree();

    @Test
    void case_1() {
        assertThat(solution.isSymmetric(TreeNode.of(1, 2, 2, 3, 4, 4, 3))).isTrue();
    }

    @Test
    void case_2() {
        assertThat(solution.isSymmetric(TreeNode.of(1, 2, 2, null, 3, null, 3))).isFalse();
    }
}