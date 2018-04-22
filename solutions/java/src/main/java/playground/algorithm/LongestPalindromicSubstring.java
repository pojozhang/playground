
package playground.algorithm;

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



        return joiner.toString();
    }
}