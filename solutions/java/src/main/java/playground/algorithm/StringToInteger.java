package playground.algorithm;

public class StringToInteger {

    public int myAtoi(String str) {
        str = str.trim();
        long result = 0;
        byte sign = 1;
        boolean hasSignSet = false, hasNumberPresent = false;

        for (int i = 0; i < str.length(); i++) {
            Character ch = str.charAt(i);

            if (!hasNumberPresent && !hasSignSet && ch == '-') {
                sign = -1;
                hasSignSet = true;
                continue;
            }

            if (!hasNumberPresent && !hasSignSet && ch == '+') {
                sign = 1;
                hasSignSet = true;
                continue;
            }

            if (ch >= '0' && ch <= '9') {
                hasNumberPresent = true;
                result = result * 10 + ch - '0';
                if (sign * result > Integer.MAX_VALUE) {
                    return Integer.MAX_VALUE;
                }

                if (sign * result < Integer.MIN_VALUE) {
                    return Integer.MIN_VALUE;
                }

                continue;
            }

            break;
        }

        return (int) (sign * result);
    }
}
