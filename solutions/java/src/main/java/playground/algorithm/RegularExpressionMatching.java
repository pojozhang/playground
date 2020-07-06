package playground.algorithm;

public class RegularExpressionMatching {

    public boolean isMatch(String s, String p) {
        return isMatch(s, 0, p, 0);
    }

    private boolean isMatch(String s, int pos1, String p, int pos2) {
        if (pos1 < s.length() && pos2 >= p.length()) {
            return false;
        }
        if (pos1 >= s.length() && pos2 >= p.length()) {
            return true;
        }
        if (pos2 < p.length() - 1 && p.charAt(pos2 + 1) == '*') {
            if (pos1 < s.length() && (p.charAt(pos2) == '.' || s.charAt(pos1) == p.charAt(pos2))) {
                return isMatch(s, pos1, p, pos2 + 2)
                        || isMatch(s, pos1 + 1, p, pos2)
                        || isMatch(s, pos1 + 1, p, pos2 + 2);
            }
            return isMatch(s, pos1, p, pos2 + 2);
        }
        if (pos1 < s.length() && (p.charAt(pos2) == '.' || s.charAt(pos1) == p.charAt(pos2))) {
            return isMatch(s, pos1 + 1, p, pos2 + 1);
        }
        return false;
    }
}
