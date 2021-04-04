package playground.algorithm;

public class WordSearch {

    private boolean[][] used;

    public boolean exist(char[][] board, String word) {
        int width = board[0].length;
        int height = board.length;
        used = new boolean[height][width];
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if (exist(board, width, height, row, column, word, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean exist(char[][] board, int weight, int height,
                          int row, int column, String word, int index) {
        if (index >= word.length()) {
            return true;
        }
        if (row < 0 || row >= height || column < 0 || column >= weight) {
            return false;
        }
        char target = word.charAt(index);
        if (used[row][column] || board[row][column] != target) {
            return false;
        }
        used[row][column] = true;
        if (exist(board, weight, height, row, column + 1, word, index + 1)
                || exist(board, weight, height, row, column - 1, word, index + 1)
                || exist(board, weight, height, row + 1, column, word, index + 1)
                || exist(board, weight, height, row - 1, column, word, index + 1)) {
            return true;
        }
        used[row][column] = false;
        return false;
    }
}
