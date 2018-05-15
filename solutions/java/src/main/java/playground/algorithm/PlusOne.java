package playground.algorithm;

import java.util.Arrays;

public class PlusOne {

    public int[] plusOne(int[] digits) {
        int[] result = Arrays.copyOf(digits, digits.length);
        result[result.length - 1] += 1;
        int carry = 0;
        for (int i = result.length - 1; i >= 0; i--) {
            int value = result[i] + carry;
            if (value < 10) {
                result[i] = value;
                carry = 0;
                break;
            }
            result[i] = value % 10;
            carry = value / 10;
        }

        if (carry > 0) {
            int[] tmp = new int[result.length + 1];
            System.arraycopy(result, 0, tmp, 1, result.length);
            tmp[0] = carry;
            result = tmp;
        }

        return result;
    }
}
