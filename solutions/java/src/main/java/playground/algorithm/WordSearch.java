package playground.algorithm;

public class WordSearch {

    public boolean exist(char[][] board, String word) {
        int width = board[0].length;
        int height = board.length;
        boolean[][] flag = new boolean[height][width];
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                flag[row][column] = false;
            }
        }

        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if (board[row][column] == word.charAt(0)) {
                    flag[row][column] = true;
                    if (exist(board, flag, width, height, row, column, word, 0)) {
                        return true;
                    }
                    flag[row][column] = false;
                }
            }
        }
        return false;
    }

    private boolean exist(char[][] board, boolean[][] flag, int width, int height, int row, int column, String word, int index) {
        index++;
        if (index >= word.length()) {
            return true;
        }
        // 向上。
        if (row > 0 && !flag[row - 1][column] && board[row - 1][column] == word.charAt(index)) {
            flag[row - 1][column] = true;
            if (exist(board, flag, width, height, row - 1, column, word, index)) {
                return true;
            }
            flag[row - 1][column] = false;
        }

        // 向右。
        if (column < width - 1 && !flag[row][column + 1] && board[row][column + 1] == word.charAt(index)) {
            flag[row][column + 1] = true;
            if (exist(board, flag, width, height, row, column + 1, word, index)) {
                return true;
            }
            flag[row][column + 1] = false;
        }

        // 向下。
        if (row < height - 1 && !flag[row + 1][column] && board[row + 1][column] == word.charAt(index)) {
            flag[row + 1][column] = true;
            if (exist(board, flag, width, height, row + 1, column, word, index)) {
                return true;
            }
            flag[row + 1][column] = false;
        }

        // 向左。
        if (column > 0 && !flag[row][column - 1] && board[row][column - 1] == word.charAt(index)) {
            flag[row][column - 1] = true;
            if (exist(board, flag, width, height, row, column - 1, word, index)) {
                return true;
            }
            flag[row][column - 1] = false;
        }
        return false;
    }
}
