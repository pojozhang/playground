package playground.algorithm;

import java.util.HashMap;
import java.util.Map;

public class LongestSubstringWithoutRepeatingCharacters {
    public int lengthOfLongestSubstring(String s) {
        int longest = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            if (!map.containsKey(s.charAt(i))) {
                map.put(s.charAt(i), i);
            } else {
                longest = Math.max(map.size(), longest);
                i = map.get(s.charAt(i)); //跳转到上次出现该字符的位置
                map.clear();
            }
        }
        longest = Math.max(map.size(), longest);
        return longest;
    }
}