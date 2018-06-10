package algorithm

import "math"

func totalNQueens(n int) int {
	sum := 0
	totalNQueensR(0, n, make([]int, n), &sum)
	return sum
}

func totalNQueensR(row, boardSize int, board []int, sum *int) {
	if row == boardSize {
		*sum++
		return
	}

	for column := 1; column <= boardSize; column++ {
		if !isNQueensValid(row, column, board) {
			continue
		}
		board[row] = column
		totalNQueensR(row+1, boardSize, board, sum)
		board[row] = 0
	}
}

func isNQueensValid(row, column int, board []int) bool {
	for r := 0; r < row; r++ {
		// 判断列及对角线是否已被使用
		// 其中判断对角线的方法：两格之间行的索引号之差等于列的索引号之差
		if board[r] == column || row-r == int(math.Abs(float64(column-board[r]))) {
			return false
		}
	}
	return true
}
