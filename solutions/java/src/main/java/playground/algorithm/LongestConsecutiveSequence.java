package playground.algorithm;

import java.util.HashMap;
import java.util.Map;

public class LongestConsecutiveSequence {

    public int longestConsecutive(int[] nums) {
        int longest = 0, left, right, sum;
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : nums) {
            // 跳过已经出现过的数字
            if (map.containsKey(n)) {
                continue;
            }
            left = map.getOrDefault(n - 1, 0);
            right = map.getOrDefault(n + 1, 0);
            sum = left + right + 1;
            map.put(n, sum);
            // 更新序列头
            map.put(n - left, sum);
            // 更新序列尾
            map.put(n + right, sum);
            longest = Math.max(longest, sum);
        }
        return longest;
    }
}
