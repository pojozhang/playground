package algorithm

func merge(nums1 []int, m int, nums2 []int, n int) {
	first, second, pos := m-1, n-1, len(nums1)-1
	for first >= 0 && second >= 0 {
		if nums2[second] > nums1[first] {
			nums1[pos] = nums2[second]
			second--
		} else {
			nums1[pos] = nums1[first]
			first--
		}
		pos--
	}

	for second >= 0 {
		nums1[pos] = nums2[second]
		second--
		pos--
	}
}
