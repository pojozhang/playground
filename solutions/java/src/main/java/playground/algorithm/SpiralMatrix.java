package playground.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpiralMatrix {

    public List<Integer> spiralOrder(int[][] matrix) {
        if (matrix.length < 1) {
            return Collections.emptyList();
        }
        return spiralOrder(matrix, 0, matrix[0].length - 1, 0, matrix.length - 1);
    }

    private List<Integer> spiralOrder(int[][] matrix, int startX, int endX, int startY, int endY) {
        List<Integer> result = new ArrayList<>();
        for (int x = startX; x <= endX && startY <= endY; x++) {
            result.add(matrix[startY][x]);
        }
        startY++;
        for (int y = startY; y <= endY && startX <= endX; y++) {
            result.add(matrix[y][endX]);
        }
        endX--;
        for (int x = endX; x >= startX && startY <= endY; x--) {
            result.add(matrix[endY][x]);
        }
        endY--;
        for (int y = endY; y >= startY && startX <= endX; y--) {
            result.add(matrix[y][startX]);
        }
        startX++;
        if (!result.isEmpty()) {
            result.addAll(spiralOrder(matrix, startX, endX, startY, endY));
        }
        return result;
    }
}
