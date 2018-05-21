package algorithm

import "math"

func findMedianSortedArrays(nums1 []int, nums2 []int) float64 {
	l := len(nums1) + len(nums2)
	if l%2 == 0 {
		return float64(findKth(nums1, nums2, l/2-1)+findKth(nums1, nums2, l/2)) / 2
	}
	return float64(findKth(nums1, nums2, l/2))
}

//参数k从0开始
func findKth(nums1, nums2 []int, k int) int {
	//约定nums1元素数量一定少于nums2，如果不是则互换参数
	if len(nums1) > len(nums2) {
		return findKth(nums2, nums1, k)
	}

	if len(nums1) == 0 {
		return nums2[k]
	}

	if k == 0 {
		return int(math.Min(float64(nums1[0]), float64(nums2[0])))
	}

	//由于nums1较短，因此k/2有可能超出范围
	p := int(math.Min(float64(len(nums1)-1), float64(k/2)))
	//确保 p + q = k - 1
	//比如我要找第9个元素（因为k从0开始，因此有10个元素），那么两个数组的下面加起来应该等于8，这样才能才是10个元素
	q := k - p - 1
	if nums1[p] < nums2[q] {
		return findKth(nums1[p+1:], nums2, k-p-1)
	}
	if nums1[p] > nums2[q] {
		return findKth(nums1, nums2[q+1:], k-q-1)
	}
	return nums1[p]
}
