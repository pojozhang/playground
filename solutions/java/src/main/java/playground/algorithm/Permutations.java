package playground.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import playground.util.Utils;

public class Permutations {

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        permute(nums, 0, result);
        return result;
    }

    private void permute(int[] nums, int start, List<List<Integer>> list) {
        if (start == nums.length - 1) {
            list.add(Arrays.stream(nums).boxed().collect(Collectors.toList()));
            return;
        }

        for (int i = start; i < nums.length; i++) {
            Utils.swap(nums, i, start);
            permute(nums, start + 1, list);
            Utils.swap(nums, i, start);
        }
    }
}
