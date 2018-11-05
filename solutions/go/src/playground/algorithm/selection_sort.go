package algorithm

// 不稳定排序。
// 最好、最坏、平均时间复杂度均为O(n^2)。
func selectionSort(nums []int) {
	for i := 0; i < len(nums)-1; i++ {
		min := i
		// 每次从未排序区域找到最小的元素。
		for j := i + 1; j < len(nums); j++ {
			if nums[j] < nums[min] {
				min = j
			}
		}
		// 把找到的最小的元素放到已排序区的末位。
		nums[i], nums[min] = nums[min], nums[i]
	}
}
