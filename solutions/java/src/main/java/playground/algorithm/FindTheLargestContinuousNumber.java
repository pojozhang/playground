package playground.algorithm;

public class FindTheLargestContinuousNumber {

    public double findTheLargestContinuousNumber(String input) {
        double max = 0;
        String[] strings = input.split("\\.");
        for (int i = 0; i < strings.length; i++) {
            if (i + 1 < strings.length) {
                max = Math.max(max, Double.parseDouble(strings[i] + "." + strings[i + 1]));
                continue;
            }
            max = Math.max(max, Double.parseDouble(strings[i]));
        }
        return max;
    }
}
