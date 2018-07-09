package algorithm

import "math"

func canJump(nums []int) bool {
	max := 0 // 记录当前单个跳跃点能到达的最远距离，不是多个跳跃点的累加距离
	for i := range nums {
		// 之前的跳跃点能到达的最远位置都小于当前点的索引，这表示当前点无法到达
		if i > max {
			return false
		}
		max = int(math.Max(float64(max), float64(i+nums[i])))
		// 已经到达最后一格
		if max >= len(nums)-1 {
			return true
		}
	}
	return false
}
