package playground.interview;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class StringTest {

    @Test
    void intern() {
        long now = new Date().getTime();
        String s1 = String.valueOf(now);
        String s2 = String.valueOf(now);
        assertNotSame(s1, s2);
        assertSame(s1.intern(), String.valueOf(now).intern());

        assertNotSame("string", new String("string"));
        assertSame("string", new String("string").intern());
    }
}
