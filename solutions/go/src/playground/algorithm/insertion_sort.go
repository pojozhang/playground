package algorithm

func insertionSort(nums []int) {
	for i := 1; i < len(nums); i++ {
		key := nums[i]
		j := i - 1
		for ; j >= 0; j-- {
			if nums[j] > key {
				nums[j+1] = nums[j]
			} else {
				break
			}
		}
		nums[j+1] = key
	}
}
