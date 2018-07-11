package algorithm

func jump(nums []int) int {
	jump := 0
	for end := len(nums) - 1; end > 0; {
		for i := range nums {
			if i+nums[i] >= end {
				end = i
				jump++
				break
			}
		}
	}
	return jump
}
