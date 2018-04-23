
package playground.algorithm;

import java.util.Arrays;
import java.util.StringJoiner;

public class LongestPalindromicSubstring {

    /**
     * Manacher算法
     */
    public String longestPalindrome(String s) {
        //向字符串插入#，使字符串是奇数位长度
        String[] array = s.split("");
        StringJoiner joiner = new StringJoiner("#", "#", "#");
        for (int i = 0; i < array.length; i++) {
            joiner.add(array[i]);
        }

        String encoded = joiner.toString();
        System.out.println(encoded);

        int[] p = new int[encoded.length()];
        int id = -1, mx = -1;
        for (int i = 0; i < encoded.length(); i++) {
            p[i] = (i <= mx ? Math.min(p[2 * id - i], mx - i) : 1);

            while (i + p[i] < encoded.length() && i - p[i] >= 0
                    && encoded.charAt(i + p[i]) == encoded.charAt(i - p[i])) {
                p[i]++;
            }

            if (i + p[i] - 1 > mx) {
                mx = i + p[i] - 1;
                id = i;
            }
        }

        mx--;

        System.out.println(Arrays.toString(p));

        return "";
    }
}