package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KthSmallestElementInASortedMatrixTest {

    private KthSmallestElementInASortedMatrix solution = new KthSmallestElementInASortedMatrix();

    @Test
    void case_1() {
        int[][] matrix = new int[][]{
                {1, 5, 9},
                {10, 11, 13},
                {12, 13, 15},
        };

        assertEquals(13, solution.kthSmallest(matrix, 8));
    }
}