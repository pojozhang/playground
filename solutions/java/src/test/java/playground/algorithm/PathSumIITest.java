package playground.algorithm;

import org.junit.jupiter.api.Test;
import playground.algorithm.common.TreeNode;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PathSumIITest {

    private PathSumII solution = new PathSumII();

    @Test
    void case_1() {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(4);
        root.right = new TreeNode(8);
        root.left.left = new TreeNode(11);
        root.right.left = new TreeNode(13);
        root.right.right = new TreeNode(4);
        root.left.left.left = new TreeNode(7);
        root.left.left.right = new TreeNode(2);
        root.right.right.left = new TreeNode(5);
        root.right.right.right = new TreeNode(11);

        List<List<Integer>> result = solution.pathSum(root, 22);

        assertThat(result).containsExactlyInAnyOrder(
                List.of(5, 4, 11, 2),
                List.of(5, 8, 4, 5)
        );
    }

    @Test
    void case_2() {
        TreeNode root = TreeNode.of(1, 2);

        List<List<Integer>> result = solution.pathSum(root, 1);

        assertThat(result).isEmpty();
    }
}