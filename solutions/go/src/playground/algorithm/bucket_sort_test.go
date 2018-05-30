package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestBucketSortCase1(t *testing.T) {
	nums := []int{3, 4, 5, 2, 1}

	bucketSort(nums, 5)

	assert.Equal(t, []int{1, 2, 3, 4, 5}, nums)
}

func TestBucketSortCase2(t *testing.T) {
	nums := []int{3, 4, 5, 5, 5, 2, 1}

	bucketSort(nums, 5)

	assert.Equal(t, []int{1, 2, 3, 4, 5, 5, 5}, nums)
}
