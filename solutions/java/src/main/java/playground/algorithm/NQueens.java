package playground.algorithm;

import java.util.ArrayList;
import java.util.List;

public class NQueens {

    public List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        solveNQueens(0, n, new ArrayList<>(), result);
        return result;
    }

    private void solveNQueens(int row, int size, List<String> board, List<List<String>> boards) {
        if (row == size) {
            boards.add(new ArrayList<>(board));
            return;
        }
        for (int column = 0; column < size; column++) {
            if (hasConflict(row, column, size, board)) {
                continue;
            }
            board.add(getRow(column, size));
            solveNQueens(row + 1, size, board, boards);
            board.remove(board.size() - 1);
        }
    }

    private boolean hasConflict(int row, int column, int size, List<String> board) {
        for (int r = 0; r < row; r++) {
            if (board.get(r).charAt(column) == 'Q') {
                return true;
            }
        }

        for (int r = row - 1, c = column - 1; r >= 0 && c >= 0; r--, c--) {
            if (board.get(r).charAt(c) == 'Q') {
                return true;
            }
        }

        for (int r = row - 1, c = column + 1; r >= 0 && c < size; r--, c++) {
            if (board.get(r).charAt(c) == 'Q') {
                return true;
            }
        }

        return false;
    }

    private String getRow(int column, int size) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < column; i++) {
            builder.append(".");
        }
        builder.append("Q");
        for (int i = column + 1; i < size; i++) {
            builder.append(".");
        }
        return builder.toString();
    }
}
