package algorithm

func firstMissingPositive(nums []int) int {
	for i := 0; i < len(nums); {
		if nums[i] < 1 || nums[i] >= len(nums) || nums[i] == nums[nums[i]-1] {
			i++
			continue
		}

		nums[i], nums[nums[i]-1] = nums[nums[i]-1], nums[i]
	}

	for i, n := range nums {
		if n != i+1 {
			return i + 1
		}
	}

	return len(nums) + 1
}
