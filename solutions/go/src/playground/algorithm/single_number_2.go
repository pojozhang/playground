package algorithm

import "strconv"

func singleNumber2(nums []int) int {
	result, bits := 0, make([]int, strconv.IntSize)

	for _, n := range nums {
		for j := uint(0); j < strconv.IntSize; j++ {
			bits[j] += (n >> j) & 1
		}
	}

	for i, b := range bits {
		if b%3 > 0 {
			result += 1 << uint(i)
		}
	}

	return result
}
