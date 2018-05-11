package playground.algorithm;

public class CountAndSay {

    public String countAndSay(int n) {
        if (n == 1) {
            return "1";
        }

        String text = countAndSay(n - 1);
        String result = "";
        for (int i = 0; i < text.length(); ) {
            int j = i + 1;
            while (j < text.length() && text.charAt(i) == text.charAt(j)) {
                j++;
            }
            result += String.valueOf(j - i) + text.charAt(i);
            i = j;
        }

        return result;
    }
}
