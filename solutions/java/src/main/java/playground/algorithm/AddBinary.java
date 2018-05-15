package playground.algorithm;

public class AddBinary {

    public String addBinary(String a, String b) {
        String result = "";
        int minLength = Math.min(a.length(), b.length());
        int carry = 0;//进位
        for (int i = 1; i <= minLength; i++) {
            int n1 = a.charAt(a.length() - i) - '0';
            int n2 = b.charAt(b.length() - i) - '0';
            if (n1 + n2 + carry > 1) {
                result = String.valueOf((n1 + n2 + carry) % 2) + result;
                carry = 1;
                continue;
            }
            result = String.valueOf(n1 + n2 + carry) + result;
            carry = 0;
        }

        String longer = a.length() > b.length() ? a : b;
        for (int i = longer.length() - minLength - 1; i >= 0; i--) {
            int n = longer.charAt(i) - '0';
            if (n + carry > 1) {
                result = String.valueOf((n + carry) % 2) + result;
                carry = 1;
                continue;
            }
            result = String.valueOf(n + carry) + result;
            carry = 0;
        }

        if (carry > 0) {
            result = "1" + result;
        }
        return result;
    }
}
