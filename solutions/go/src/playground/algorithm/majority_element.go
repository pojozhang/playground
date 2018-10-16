package algorithm

func majorityElement(nums []int) int {
	if len(nums) == 1 {
		return nums[0]
	}

	left := majorityElement(nums[:len(nums)/2])
	right := majorityElement(nums[len(nums)/2:])

	if left == right {
		return left
	}

	leftCount, rightCount := 0, 0
	for _, n := range nums {
		if n == left {
			leftCount++
		} else if n == right {
			rightCount++
		}
	}
	if leftCount >= rightCount {
		return left
	}
	return right
}
