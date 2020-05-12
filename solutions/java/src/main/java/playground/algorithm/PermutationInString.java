package playground.algorithm;

import java.util.Arrays;

public class PermutationInString {

    public boolean checkInclusion(String s1, String s2) {
        int[] map1 = new int[26];
        for (char c : s1.toCharArray()) {
            map1[c - 'a']++;
        }

        for (int i = 0; i <= s2.length() - s1.length(); i++) {
            if (map1[s2.charAt(i) - 'a'] == 0) {
                continue;
            }
            int[] map2 = new int[26];
            for (int j = i; j < i + s1.length(); j++) {
                map2[s2.charAt(j) - 'a']++;
            }
            if (Arrays.compare(map1, map2) == 0) {
                return true;
            }
        }
        return false;
    }
}
