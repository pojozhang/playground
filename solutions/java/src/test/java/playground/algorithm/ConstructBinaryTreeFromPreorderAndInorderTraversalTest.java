package playground.algorithm;

import org.junit.jupiter.api.Test;
import playground.algorithm.common.TreeNode;

import static org.assertj.core.api.Assertions.assertThat;

class ConstructBinaryTreeFromPreorderAndInorderTraversalTest {

    private ConstructBinaryTreeFromPreorderAndInorderTraversal solution = new ConstructBinaryTreeFromPreorderAndInorderTraversal();

    @Test
    void case_1() {
        TreeNode root = solution.buildTree(new int[]{3, 9, 20, 15, 7}, new int[]{9, 3, 15, 20, 7});

        assertThat(root.val).isEqualTo(3);
        assertThat(root.left.val).isEqualTo(9);
        assertThat(root.right.val).isEqualTo(20);
        assertThat(root.right.left.val).isEqualTo(15);
        assertThat(root.right.right.val).isEqualTo(7);
    }
}