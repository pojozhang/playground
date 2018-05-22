package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestMergeCase1(t *testing.T) {
	nums1, nums2 := []int{1, 2, 3, 0, 0, 0}, []int{2, 5, 6}

	merge(nums1, 3, nums2, 3)

	assert.Equal(t, []int{1, 2, 2, 3, 5, 6}, nums1)
}

func TestMergeCase2(t *testing.T) {
	nums1, nums2 := []int{0}, []int{1}

	merge(nums1, 0, nums2, 1)

	assert.Equal(t, []int{1}, nums1)
}

func TestMergeCase3(t *testing.T) {
	nums1, nums2 := []int{2, 0}, []int{1}

	merge(nums1, 1, nums2, 1)

	assert.Equal(t, []int{1, 2}, nums1)
}

func TestMergeCase4(t *testing.T) {
	nums1, nums2 := []int{0, 0, 0, 0, 0}, []int{1, 2, 3, 4, 5}

	merge(nums1, 0, nums2, 5)

	assert.Equal(t, []int{1, 2, 3, 4, 5}, nums1)
}
