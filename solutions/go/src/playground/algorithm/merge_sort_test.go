package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestMergeSortCase1(t *testing.T) {
	nums := []int{4, 3, 0, 1, 2}

	mergeSort(nums)

	assert.Equal(t, []int{0, 1, 2, 3, 4}, nums)
}
