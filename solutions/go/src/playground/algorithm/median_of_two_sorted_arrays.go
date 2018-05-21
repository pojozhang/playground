package algorithm

import "math"

func findMedianSortedArrays(nums1 []int, nums2 []int) float64 {
	l := len(nums1) + len(nums2)
	if l%2 == 0 {
		return float64(findKth(nums1, nums2, l/2-1)+findKth(nums1, nums2, l/2)) / 2
	}
	return float64(findKth(nums1, nums2, l/2))
}

func findKth(nums1, nums2 []int, k int) int {
	if len(nums1) > len(nums2) {
		return findKth(nums2, nums1, k)
	}

	if len(nums1) == 0 {
		return nums2[k]
	}

	if k == 0 {
		return int(math.Min(float64(nums1[0]), float64(nums2[0])))
	}

	p := int(math.Min(float64(len(nums1)-1), float64(k/2)))
	q := k - p - 1
	if nums1[p] < nums2[q] {
		return findKth(nums1[p+1:], nums2, k-p-1)
	}
	if nums1[p] > nums2[q] {
		return findKth(nums1, nums2[q+1:], k-q-1)
	}
	return nums1[p]
}
