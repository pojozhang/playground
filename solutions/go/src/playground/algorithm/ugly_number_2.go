package algorithm

import "math"

func nthUglyNumber(n int) int {
	uglyNumbers := make([]int, n)
	uglyNumbers[0] = 1
	var ptr2, ptr3, ptr5 int

	for i := 1; i < n; i++ {
		uglyNumbers[i] = int(math.Min(float64(uglyNumbers[ptr2]*2), math.Min(float64(uglyNumbers[ptr3]*3), float64(uglyNumbers[ptr5]*5))))

		if uglyNumbers[i] == uglyNumbers[ptr2]*2 {
			ptr2++
		}
		if uglyNumbers[i] == uglyNumbers[ptr3]*3 {
			ptr3++
		}
		if uglyNumbers[i] == uglyNumbers[ptr5]*5 {
			ptr5++
		}
	}

	return uglyNumbers[n-1]
}
