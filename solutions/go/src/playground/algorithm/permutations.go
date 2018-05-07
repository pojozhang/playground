package algorithm

func permute(nums []int) [][]int {
	var result [][]int
	permuteR(nums, 0, &result)
	return result
}

func permuteR(nums []int, start int, result *[][]int) {
	if start == len(nums)-1 {
		*result = append(*result, nums)
		return
	}

	for i := start; i < len(nums); i++ {
		nums[start], nums[i] = nums[i], nums[start]
		tmp := make([]int, len(nums))
		copy(tmp, nums)
		permuteR(tmp, start+1, result)
		nums[start], nums[i] = nums[i], nums[start]
	}
}
