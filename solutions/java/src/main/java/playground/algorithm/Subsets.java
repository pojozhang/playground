package playground.algorithm;

import java.util.ArrayList;
import java.util.List;

public class Subsets {

    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> subsets = new ArrayList<>();
        subsets(nums, 0, new ArrayList<>(), subsets);
        return subsets;
    }

    private void subsets(int[] nums, int start, List<Integer> subset, List<List<Integer>> subsets) {
        subsets.add(new ArrayList<>(subset));
        for (int i = start; i < nums.length; i++) {
            subset.add(nums[i]);
            subsets(nums, i + 1, subset, subsets);
            subset.remove(subset.size() - 1);
        }
    }
}
