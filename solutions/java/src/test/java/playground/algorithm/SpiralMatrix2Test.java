package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpiralMatrix2Test {

    private SpiralMatrix2 solution = new SpiralMatrix2();

    @Test
    void case_1() {
        int[][] matrix = solution.generateMatrix(3);

        assertArrayEquals(new int[]{1, 2, 3}, matrix[0]);
        assertArrayEquals(new int[]{8, 9, 4}, matrix[1]);
        assertArrayEquals(new int[]{7, 6, 5}, matrix[2]);
    }
}