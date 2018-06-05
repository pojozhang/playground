package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestRadixSortCase1(t *testing.T) {
	nums := []int{53, 3, 542, 748, 14, 214, 154, 63, 616}

	radixSort(nums)

	assert.Equal(t, []int{3, 14, 53, 63, 154, 214, 542, 616, 748}, nums)
}
