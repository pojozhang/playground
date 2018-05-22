package algorithm

import "math"

func longestConsecutive(nums []int) int {
	longest, m := 0, make(map[int]int)
	for _, n := range nums {
		if _, ok := m[n]; ok {
			continue
		}
		l, r := m[n-1], m[n+1]
		m[n] = l + r + 1
		m[n-l], m[n+r] = m[n], m[n]
		longest = int(math.Max(float64(longest), float64(m[n])))
	}
	return longest
}
