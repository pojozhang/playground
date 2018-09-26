package playground.interview;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BitwiseTest {

    /*
     * 带符号右移是指对数字的二进制表示进行右移。
     * 如果操作数是正数则高位补0，如果是负数则补1（也就是补符号位）。 如果操作数是0，那么结果就是0。
     * 浮点类型不能进行移位操作，short和long类型的变量可以进行移位操作，char、byte、short在移位前会转换成int。
     */
    @Test
    void right_shift() {
        // 在不溢出的情况下，正数右移n位等价于除以2的n次方。
        // 101（十进制的5）右移1位后为10（十进制的2），等价于 5除以2
        assertEquals(2, 5 >> 1);

        // 10 >> 2 等价于 10除以2的2次方
        assertEquals(2, 10 >> 2);

        // 负数右移n位并不等价于除以2的n次方。
        assertNotEquals(-2, -5 >> 1);

        // 对于int来说，一个数字右移32位等于本身
        assertEquals(5, 5 >> 32);

        // 对于int来说，如果移位超过32位，会取模后再进行移位，如右移40位，等价于移40%32=8位
        assertEquals(5 >> 8, 5 >> 40);
    }

    /*
     * 对数字的二进制表示进行左移，右边低位补0。
     */
    @Test
    void left_shift() {
        // 在不溢出的情况下，左移n位等价于乘以2的n次方。
        assertEquals(10, 5 << 1);
        assertEquals(-10, -5 << 1);
    }

    /*
     * 无符号右移无论正负都在高位补0。
     * 不存在无符号左移。
     */
    @Test
    void unsigned_right_shift() {
        assertEquals(2, 5 >>> 1);

        // 高位补0
        // 11111111111111111111111111111011(十进制的-5)无符号右移一位后得到01111111111111111111111111111101
        assertTrue((-5 >>> 1) > 0);
    }
}
