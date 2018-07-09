package algorithm

func spiralOrder(matrix [][]int) []int {
	if len(matrix) == 0 {
		return nil
	}

	result := make([]int, 0)
	up, down, left, right := 0, len(matrix)-1, 0, len(matrix[0])-1

	for left <= right || up <= down {
		for l := left; up <= down && l <= right; l++ {
			result = append(result, matrix[up][l])
		}
		up++

		for u := up; left <= right && u <= down; u++ {
			result = append(result, matrix[u][right])
		}
		right--

		for r := right; up <= down && r >= left; r-- {
			result = append(result, matrix[down][r])
		}
		down--

		for d := down; left <= right && d >= up; d-- {
			result = append(result, matrix[d][left])
		}
		left++
	}

	return result
}
