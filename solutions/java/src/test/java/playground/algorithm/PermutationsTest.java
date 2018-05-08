package playground.algorithm;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class PermutationsTest {

    private Permutations solution = new Permutations();

    @Test
    void case_1() {
        List<List<Integer>> result = solution.permute(new int[]{1, 2, 3});

        assertEquals(6, result.size());
        assertIterableEquals(List.of(1, 2, 3), result.get(0));
        assertIterableEquals(List.of(1, 3, 2), result.get(1));
        assertIterableEquals(List.of(2, 1, 3), result.get(2));
        assertIterableEquals(List.of(2, 3, 1), result.get(3));
        assertIterableEquals(List.of(3, 2, 1), result.get(4));
        assertIterableEquals(List.of(3, 1, 2), result.get(5));
    }
}