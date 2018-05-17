package playground.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CombinationSum {

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);

        //原地去重并返回数组的长度
        int length = 1;
        for (int i = 0; i < candidates.length; i++) {
            if (candidates[i] != candidates[length - 1]) {
                candidates[length++] = candidates[i];
            }
        }

        List<List<Integer>> result = new ArrayList<>();
        combinationSum(candidates, 0, length, target, new ArrayList<>(), result);
        return result;
    }

    private void combinationSum(int[] candidates, int start, int length, int target, List<Integer> combination, List<List<Integer>> result) {
        if (target < 0) {
            return;
        }
        if (target == 0) {
            result.add(new ArrayList<>(combination));
        }

        for (int i = start; i < length; i++) {
            combination.add(candidates[i]);
            combinationSum(candidates, i, length, target - candidates[i], combination, result);
            combination.remove(combination.size() - 1);
        }
    }
}
