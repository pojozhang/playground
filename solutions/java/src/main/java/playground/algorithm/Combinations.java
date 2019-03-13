package playground.algorithm;

import java.util.ArrayList;
import java.util.List;

public class Combinations {

    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        combine(1, n, 0, k, new ArrayList<>(), result);
        return result;
    }

    private void combine(int start, int end, int depth, int length, List<Integer> list, List<List<Integer>> result) {
        if (depth == length) {
            result.add(new ArrayList<>(list));
            return;
        }

        for (int i = start; i <= end; i++) {
            list.add(i);
            combine(i + 1, end, depth + 1, length, list, result);
            list.remove(list.size() - 1);
        }
    }
}
