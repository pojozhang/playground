package algorithm

func solveNQueens(n int) [][]string {
	result := make([][]string, 0)

	// 初始化棋盘
	board := make([][]bool, n)
	for i := 0; i < n; i++ {
		board[i] = make([]bool, n)
	}

	// 递归调用
	solveNQueensR(board, n, n, 0, &result)

	return result
}

func solveNQueensR(board [][]bool, width, height, row int, result *[][]string) {
	if row == height {
		solution := make([]string, height)
		for i, r := range board {
			s := ""
			for _, c := range r {
				if c {
					s += "Q"
				} else {
					s += "."
				}
			}
			solution[i] = s
		}
		*result = append(*result, solution)
		return
	}

	for c := 0; c < width; c++ {
		board[row][c] = true
		if !hasConflict(board, width, height, row, c) {
			solveNQueensR(board, width, height, row+1, result)
		}
		board[row][c] = false
	}
}

func hasConflict(board [][]bool, width, height, row, column int) bool {
	for i := 0; i < row; i++ {
		if board[i][column] {
			return true
		}
	}

	for i := row + 1; i < width; i++ {
		if board[i][column] {
			return true
		}
	}

	for i := 0; i < column; i++ {
		if board[row][i] {
			return true
		}
	}

	for i := column + 1; i < height; i++ {
		if board[row][i] {
			return true
		}
	}

	for i, j := row-1, column-1; i >= 0 && j >= 0; i, j = i-1, j-1 {
		if board[i][j] {
			return true
		}
	}

	for i, j := row+1, column+1; i < width && j < height; i, j = i+1, j+1 {
		if board[i][j] {
			return true
		}
	}

	for i, j := row-1, column+1; i >= 0 && j < height; i, j = i-1, j+1 {
		if board[i][j] {
			return true
		}
	}

	for i, j := row+1, column-1; i < width && j >= 0; i, j = i+1, j-1 {
		if board[i][j] {
			return true
		}
	}

	return false
}
