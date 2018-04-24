package playground.algorithm;

public class LongestCommonPrefix {

    public String longestCommonPrefix(String[] strs) {
        if (strs.length < 1) {
            return "";
        }

        String key = strs[0];
        int len = key.length();
        for (int i = 1; i < strs.length; i++) {
            len = Math.min(len, strs[i].length());
            for (int j = 0; j < len; j++) {
                if (key.charAt(j) != strs[i].charAt(j)) {
                    len = j;
                    break;
                }
            }
        }

        return strs[0].substring(0, len);
    }
}
