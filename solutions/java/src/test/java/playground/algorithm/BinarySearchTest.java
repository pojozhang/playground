package playground.algorithm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BinarySearchTest {

    private BinarySearch solution = new BinarySearch();

    @Test
    void case_1() {
        assertEquals(3, solution.search(new int[]{0, 1, 2, 3, 4}, 3));
    }

    @Test
    void case_2() {
        assertEquals(-1, solution.search(new int[]{0, 1, 2, 3, 4}, 6));
    }
}