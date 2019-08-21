package playground.algorithm;

public class ReverseWordsInAString3 {

    public String reverseWords(String s) {
        StringBuilder builder = new StringBuilder();
        int checkpoint = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                for (int j = i - 1; j >= checkpoint; j--) {
                    builder.append(s.charAt(j));
                }
                builder.append(' ');
                checkpoint = i + 1;
            }
        }
        for (int j = s.length() - 1; j >= checkpoint; j--) {
            builder.append(s.charAt(j));
        }
        return builder.toString();
    }
}
