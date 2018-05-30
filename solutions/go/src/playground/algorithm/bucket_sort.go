package algorithm

func bucketSort(nums []int, max int) {
	buckets := make([]int, max+1)

	for _, n := range nums {
		buckets[n]++
	}

	pos := 0
	for i, bucket := range buckets {
		for count := 0; count < bucket; count++ {
			nums[pos] = i
			pos++
		}
	}
}
