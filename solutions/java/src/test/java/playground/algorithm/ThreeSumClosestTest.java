package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThreeSumClosestTest {

    private ThreeSumClosest solution = new ThreeSumClosest();

    @Test
    void case_1() {
        assertEquals(2, solution.threeSumClosest(new int[]{-1, 2, 1, -4}, 1));
    }

    @Test
    void case_2() {
        assertEquals(3, solution.threeSumClosest(new int[]{0, 1, 2}, 0));
    }
}