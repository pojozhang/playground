package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BubbleSortTest {

    private BubbleSort solution = new BubbleSort();

    @Test
    void case_1() {
        var array = new int[]{4, 2, 5, 6, 7};
        solution.sort(array);
        assertArrayEquals(array, new int[]{2, 4, 5, 6, 7});
    }
}