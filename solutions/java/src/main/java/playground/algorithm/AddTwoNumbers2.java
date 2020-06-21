package playground.algorithm;

public class AddTwoNumbers2 {

    public int add(int a, int b) {
        if (b == 0) {
            return a;
        }

        // 和s = 非进位和n + 进位c。
        // 非进位n = a ^ b，进位c = (a & b) << 1。
        // 由于需要把n加上c，因此继续对n和c进行递归。
        return add(a ^ b, (a & b) << 1);
    }
}
