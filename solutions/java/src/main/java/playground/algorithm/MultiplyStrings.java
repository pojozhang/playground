package playground.algorithm;

import java.util.LinkedList;
import java.util.Queue;

public class MultiplyStrings {

    public String multiply(String num1, String num2) {
        if ("0".equals(num1) || "0".equals(num2)) {
            return "0";
        }

        Queue<String> queue = new LinkedList<>();
        for (int j = num2.length() - 1; j >= 0; j--) {
            int n2 = num2.charAt(j) - '0';
            int carry = 0;
            StringBuilder number = new StringBuilder();
            for (int b = num2.length() - 1; b > j; b--) {
                number.append("0");
            }
            for (int i = num1.length() - 1; i >= 0 || carry > 0; i--) {
                int n1 = i < 0 ? 0 : num1.charAt(i) - '0';
                int product = n1 * n2 + carry;
                carry = product / 10;
                number.append(product % 10);
            }
            queue.add(number.reverse().toString());
        }

        String result = queue.poll();
        while (!queue.isEmpty()) {
            result = add(result, queue.poll());
        }
        return result;
    }

    private String add(String num1, String num2) {
        StringBuilder builder = new StringBuilder();
        int carry = 0;
        for (int i = num1.length() - 1, j = num2.length() - 1; i >= 0 || j >= 0 || carry > 0; i--, j--) {
            int n1 = i < 0 ? 0 : num1.charAt(i) - '0';
            int n2 = j < 0 ? 0 : num2.charAt(j) - '0';
            int sum = n1 + n2 + carry;
            carry = sum / 10;
            builder.append(sum % 10);
        }
        return builder.reverse().toString();
    }
}
