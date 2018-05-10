
package playground.algorithm;

import java.util.StringJoiner;

public class LongestPalindromicSubstring {

    /**
     * Manacher算法
     */
    public String longestPalindrome(String s) {
        //向字符串插入#，使字符串是奇数位长度
        String[] strings = s.split("");
        StringJoiner joiner = new StringJoiner("#", "#", "#");
        for (int i = 0; i < strings.length; i++) {
            joiner.add(strings[i]);
        }
        String encoded = joiner.toString();

        int[] p = new int[encoded.length()];
        int id = 0, mx = 0, pos = 0;
        for (int i = 0; i < encoded.length(); i++) {
            p[i] = (i < mx ? Math.min(p[2 * id - i], mx - i) : 1);

            while (i - p[i] >= 0 && i + p[i] < encoded.length()
                && encoded.charAt(i + p[i]) == encoded.charAt(i - p[i])) {
                p[i]++;
            }

            if (i + p[i] > mx) {
                mx = i + p[i];
                id = i;
            }

            // 记录最长回文子串的位置
            if (p[pos] < p[i]) {
                pos = i;
            }
        }

        int len = p[pos] - 1;
        return encoded.substring(pos - len, pos + len + 1).replace("#", "");
    }
}