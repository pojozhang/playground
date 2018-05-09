package playground.algorithm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ContainerWithMostWaterTest {

    private ContainerWithMostWater solution = new ContainerWithMostWater();

    @Test
    void case_1() {
        assertEquals(2, solution.maxArea(new int[]{1, 3, 2}));
    }
}