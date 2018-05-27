package playground.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CombinationSum2 {

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);
        combinationSum(candidates, 0, target, new ArrayList<>(), result);
        return result;
    }

    private void combinationSum(int[] candidates, int start, int target, List<Integer> combination, List<List<Integer>> result) {
        if (target < 0) {
            return;
        }

        if (target == 0) {
            result.add(new ArrayList<>(combination));
            return;
        }

        Integer last = null;
        for (int i = start; i < candidates.length; i++) {
            if (Objects.equals(last, candidates[i])) {
                continue;
            }
            last = candidates[i];
            combination.add(last);
            combinationSum(candidates, i + 1, target - last, combination, result);
            combination.remove(combination.size() - 1);
        }
    }
}
