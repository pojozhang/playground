package algorithm

import "math"

func maxArea(height []int) int {
	max := 0
	for l, r := 0, len(height)-1; l < r; {
		max = int(math.Max(float64(max), float64(r-l)*math.Min(float64(height[l]), float64(height[r]))))
		if height[l] < height[r] {
			l++
		} else {
			r--
		}
	}
	return max
}
