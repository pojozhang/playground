package algorithm

func combine(n int, k int) [][]int {
	result := make([][]int, 0)
	combineR(1, n, 0, k, make([]int, k), &result)
	return result
}

func combineR(start, end int, depth, length int, combine []int, result *[][]int) {
	if depth >= length {
		temp := make([]int, length)
		copy(temp, combine)
		*result = append(*result, temp)
		return
	}

	for n := start; n <= end; n++ {
		combine[depth] = n
		combineR(n+1, end, depth+1, length, combine, result)
	}
}
