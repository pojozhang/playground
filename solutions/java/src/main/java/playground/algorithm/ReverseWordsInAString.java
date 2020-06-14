package playground.algorithm;

import java.util.Arrays;

public class ReverseWordsInAString {

    public String reverseWords(String s) {
        return Arrays.stream(s.split(" "))
                .filter(word -> !"".equals(word))
                .reduce((s1, s2) -> s2 + " " + s1)
                .orElse("");
    }
}
