package playground.algorithm;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class ThreeSumTest {

    private ThreeSum solution = new ThreeSum();

    @Test
    void case_1() {
        List<List<Integer>> result = solution.threeSum(new int[]{-1, 0, 1, 2, -1, -4});

        assertEquals(2, result.size());
        assertIterableEquals(List.of(-1, -1, 2), result.get(0));
        assertIterableEquals(List.of(-1, 0, 1), result.get(1));
    }
}