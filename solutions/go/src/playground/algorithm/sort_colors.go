package algorithm

func sortColors(nums []int) {
	// 从左往右看，pos0、pos2分别指向最后一个0和第一个2的位置。
	pos0, pos2 := 0, len(nums)-1
	for i := 0; i <= pos2; {
		if nums[i] == 2 {
			nums[pos2], nums[i] = nums[i], nums[pos2]
			pos2--
		} else if nums[i] == 0 {
			nums[pos0], nums[i] = nums[i], nums[pos0]
			pos0++
			i++
		} else {
			i++
		}
	}
}
