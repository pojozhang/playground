package algorithm

func generate(numRows int) [][]int {
	result := make([][]int, numRows)
	for row := 0; row < numRows; row++ {
		result[row] = make([]int, row+1)
	}
	for row := 0; row < numRows; row++ {
		for col := 0; col < row+1; col++ {
			if col == 0 || col == row {
				result[row][col] = 1
			} else {
				result[row][col] = result[row-1][col-1] + result[row-1][col]
			}
		}
	}
	return result
}
