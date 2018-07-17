package algorithm

func searchMatrix(matrix [][]int, target int) bool {
	if len(matrix) == 0 {
		return false
	}

	// 从矩阵右上角或左下角开始遍历，每次排除一行或一列
	for row, col := 0, len(matrix[0])-1; row < len(matrix) && col >= 0; {
		if matrix[row][col] == target {
			return true
		}
		if matrix[row][col] < target {
			row++
		} else {
			col--
		}
	}
	return false
}
