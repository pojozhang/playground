package playground.algorithm;

import org.junit.jupiter.api.Test;
import playground.algorithm.common.TreeNode;

import static org.assertj.core.api.Assertions.assertThat;

class KthSmallestElementInABstTest {

    @Test
    void case_1() {
        KthSmallestElementInABst solution = new KthSmallestElementInABst();

        int result = solution.kthSmallest(TreeNode.of(3, 1, 4, null, 2), 1);

        assertThat(result).isEqualTo(1);
    }

    @Test
    void case_2() {
        KthSmallestElementInABst solution = new KthSmallestElementInABst();

        int result = solution.kthSmallest(TreeNode.of(5, 3, 6, 2, 4, null, null, 1), 3);

        assertThat(result).isEqualTo(3);
    }
}