package playground.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * Whenever a new element n is inserted into the map, do two things:
 * See if n - 1 and n + 1 exist in the map, and if so, it means there is an existing sequence next to n. Variables left and right will be the length of those two sequences, while 0 means there is no sequence and n will be the boundary point later. Store (left + right + 1) as the associated value to key n into the map.
 * Use left and right to locate the other end of the sequences to the left and right of n respectively, and replace the value with the new length.
 * Everything inside the for loop is O(1) so the total time is O(n)
 */
public class LongestConsecutiveSequence {

    public int longestConsecutive(int[] nums) {

        Map<Integer, Integer> cache = new HashMap<>();
        int longest = 0;

        for (int n : nums) {
            if (!cache.containsKey(n)) {
                int l = cache.getOrDefault(n - 1, 0);
                int r = cache.getOrDefault(n + 1, 0);
                int tmp = l + r + 1;

                cache.put(n, tmp);
                cache.put(n - l, tmp);
                cache.put(n + r, tmp);

                longest = Math.max(longest, tmp);
            }
        }
        return longest;
    }

}
