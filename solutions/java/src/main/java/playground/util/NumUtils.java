package playground.util;

public class NumUtils {

    /**
     * 查找位数
     *
     * @param num 查找数字
     * @return 数字位数
     */
    public static Integer findDigit(Integer num) {
        if (num == null) return null;
        if (num == 0) return 1;
        Integer count = 0;
        while (num > 0) {
            num = num / 10;
            count++;
        }
        return count;
    }

}
