package playground.algorithm;

import playground.algorithm.common.TreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PathSumII {

    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<List<Integer>> paths = new LinkedList<>();
        pathSum(root, 0, sum, new LinkedList<>(), paths);
        return paths;
    }

    public void pathSum(TreeNode root, int sum, int target, List<Integer> path, List<List<Integer>> paths) {
        path.add(root.val);
        sum += root.val;
        if (sum == target && root.left == null && root.right == null) {
            paths.add(new ArrayList<>(path));
            path.remove(path.size() - 1);
            return;
        }
        if (root.left != null) {
            pathSum(root.left, sum, target, path, paths);
        }
        if (root.right != null) {
            pathSum(root.right, sum, target, path, paths);
        }
        path.remove(path.size() - 1);
    }
}
