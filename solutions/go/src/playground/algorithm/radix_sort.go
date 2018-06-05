package algorithm

import (
	"math"
)

func radixSort(nums []int) {
	if len(nums) < 1 {
		return
	}

	max := nums[0]
	for _, n := range nums {
		max = int(math.Max(float64(max), float64(n)))
	}

	exp := 1
	for max/(exp*10) > 0 {
		exp *= 10
	}

	for exp := 1; max/exp > 0; exp *= 10 {
		var buckets [10]int
		// 把当前位的数字放入桶中
		for _, n := range nums {
			buckets[n/exp%10]++
		}

		for i := 1; i < len(buckets); i++ {
			buckets[i] += buckets[i-1] // buckets[i]记录了前i位（包括第i位）的元素个数
		}

		temp := make([]int, len(nums))
		copy(temp, nums)
		for i := len(nums) - 1; i >= 0; i-- {
			key := temp[i] / exp % 10
			buckets[key] -= 1
			nums[buckets[key]] = temp[i]
		}
	}
}
