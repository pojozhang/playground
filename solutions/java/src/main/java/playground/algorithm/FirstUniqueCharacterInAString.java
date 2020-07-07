package playground.algorithm;

public class FirstUniqueCharacterInAString {

    public int firstUniqChar(String s) {
        int[] count = new int[26];
        for (int i = 0; i < count.length; i++) {
            count[i] = 0;
        }
        for (char ch : s.toCharArray()) {
            count[ch - 'a']++;
        }
        for (int i = 0; i < s.length(); i++) {
            if (count[s.charAt(i) - 'a'] == 1) {
                return i;
            }
        }
        return -1;
    }
}
