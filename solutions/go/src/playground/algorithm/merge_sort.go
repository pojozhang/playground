package algorithm

func mergeSort(nums []int) {
	copy(nums, mergeSortR(nums))
}

func mergeSortR(nums []int) []int {
	if len(nums) <= 1 {
		return nums
	}

	mid := len(nums) / 2
	left := mergeSortR(nums[:mid])
	right := mergeSortR(nums[mid:])

	temp := make([]int, len(nums))
	var i, l, r int
	for l < len(left) && r < len(right) {
		if left[l] <= right[l] {
			temp[i] = left[l]
			l++
		} else {
			temp[i] = right[r]
			r++
		}
		i++
	}

	for ; l < len(left); i, l = i+1, l+1 {
		temp[i] = left[l]
	}

	for ; r < len(right); i, r = i+1, r+1 {
		temp[i] = right[r]
	}

	return temp
}
