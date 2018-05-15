package playground.algorithm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ClimbingStairsTest {

    private ClimbingStairs solution = new ClimbingStairs();

    @Test
    void case_1() {
        assertEquals(2, solution.climbStairs(2));
    }

    @Test
    void case_2() {
        assertEquals(3, solution.climbStairs(3));
    }
}