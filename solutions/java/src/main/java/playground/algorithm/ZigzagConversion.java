package playground.algorithm;

public class ZigzagConversion {

    public String convert(String s, int numRows) {
        if (numRows < 2) {
            return s;
        }

        StringBuilder[] builders = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) {
            builders[i] = new StringBuilder();
        }

        for (int i = 0, j = 0, increment = 1; j < s.length(); i += increment, j++) {
            builders[i].append(s.charAt(j));
            if (i == 0) {
                increment = 1;
            }
            if (i == numRows - 1) {
                increment = -1;
            }
        }

        String result = "";
        for (int i = 0; i < numRows; i++) {
            result += builders[i].toString();
        }

        return result;
    }
}
