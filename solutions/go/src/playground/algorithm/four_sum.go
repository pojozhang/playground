package algorithm

import "sort"

func fourSum(nums []int, target int) [][]int {
	var result [][]int
	sort.Ints(nums)
	for i := 0; i < len(nums)-3; i++ {
		if i > 0 && nums[i] == nums[i-1] {
			continue
		}
		for j := i + 1; j < len(nums)-2; j++ {
			if j > i+1 && nums[j] == nums[j-1] {
				continue
			}
			for l, r := j+1, len(nums)-1; l < r; {
				if nums[i]+nums[j]+nums[l]+nums[r] == target {
					result = append(result, []int{nums[i], nums[j], nums[l], nums[r]})
					l++
					r--
					for l < r && nums[l] == nums[l-1] {
						l++
					}
					for l < r && nums[r] == nums[r+1] {
						r--
					}
				} else if nums[i]+nums[j]+nums[l]+nums[r] < target {
					l++
					for l < r && nums[l] == nums[l-1] {
						l++
					}
				} else {
					r--
					for l < r && nums[r] == nums[r+1] {
						r--
					}
				}
			}
		}
	}
	return result
}
