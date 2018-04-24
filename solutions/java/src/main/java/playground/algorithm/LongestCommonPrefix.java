package playground.algorithm;

public class LongestCommonPrefix {

    public String longestCommonPrefix(String[] strs) {
        if (strs.length < 1) {
            return "";
        }

        int pos = -1;
        boolean interrupt = false;
        for (; !interrupt && pos + 1 < strs[0].length(); pos++) {
            char key = strs[0].charAt(pos + 1);
            for (int i = 1; i < strs.length; i++) {
                if (!(pos + 1 < strs[i].length() && strs[i].charAt(pos + 1) == key)) {
                    interrupt = true;
                    break;
                }
            }
        }

        if (pos < 0) {
            return "";
        }

        return strs[0].substring(0, pos);
    }
}
