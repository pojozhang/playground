package playground.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NumUtilsTest {

    @Test
    void case01() {
        Assertions.assertNull(NumUtils.findDigit(null));
    }

    @Test
    void case02() {
        Integer num = 0;
        Assertions.assertEquals(Integer.valueOf(1), NumUtils.findDigit(num));
    }

    @Test
    void case03() {
        Integer num = 990;
        Assertions.assertEquals(Integer.valueOf(3), NumUtils.findDigit(num));
    }

    @Test
    void case04() {
        Integer num = 1_000_000_000;
        Assertions.assertEquals(Integer.valueOf(10), NumUtils.findDigit(num));
    }

}
