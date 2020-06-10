package playground.algorithm;

import java.util.ArrayList;
import java.util.List;

public class ContinuesSequenceWithSum {

    public int[][] findContinuousSequence(int target) {
        List<int[]> result = new ArrayList<>();
        int low = 1, high = 2;
        while (low <= target / 2) {
            int sum = 0;
            for (int n = low; n <= high; n++) {
                sum += n;
            }
            if (sum < target) {
                high++;
            } else if (sum > target) {
                low++;
            } else {
                int[] sequence = new int[high - low + 1];
                for (int n = low; n <= high; n++) {
                    sequence[n - low] = n;
                }
                result.add(sequence);
                low++;
                high++;
            }
        }
        return result.toArray(new int[0][0]);
    }
}
