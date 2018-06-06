package algorithm

const boardSize = 9

func solveSudoku(board [][]byte) {
	solveSudokuR(board, 0, 0)
}

func solveSudokuR(board [][]byte, row, column int) bool {
	if column >= boardSize {
		column = 0
		row++
	}

	if row >= boardSize {
		return true
	}

	if board[row][column] != '.' {
		return solveSudokuR(board, row, column+1)
	}

	for n := '1'; n <= '9'; n++ {
		if isSudokuConflict(board, row, column, byte(n)) {
			continue
		}
		board[row][column] = byte(n)
		if solveSudokuR(board, row, column+1) {
			return true
		}
		board[row][column] = '.'
	}

	return false
}

func isSudokuConflict(board [][]byte, row, column int, num byte) bool {
	for r := 0; r < boardSize; r++ {
		if board[r][column] == num {
			return true
		}
	}

	for c := 0; c < boardSize; c++ {
		if board[row][c] == num {
			return true
		}
	}

	for x := row / 3 * 3; x < row/3*3+3; x++ {
		for y := column / 3 * 3; y < column/3*3+3; y++ {
			if board[x][y] == num {
				return true
			}
		}
	}

	return false
}
