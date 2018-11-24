package algorithm

func findKthLargest(nums []int, k int) int {
	n, _ := findKthLargestR(nums, 0, len(nums)-1, k-1)
	return n
}

func findKthLargestR(nums []int, start, end, k int) (int, bool) {
	key := nums[start]
	left, right := start, end
	for ; left < right; {
		for ; left < right && nums[right] < key; right-- {
		}
		for ; left < right && nums[left] >= key; left++ {
		}
		nums[left], nums[right] = nums[right], nums[left]
	}
	nums[start], nums[left] = nums[left], nums[start]
	if left == k {
		return key, true
	}
	if left > k {
		return findKthLargestR(nums, start, left-1, k)
	}
	return findKthLargestR(nums, left+1, end, k)
}
