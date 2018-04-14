package algorithm

import (
	"github.com/stretchr/testify/assert"
	"testing"
)

func TestQuickSort(t *testing.T) {
	nums := []int{4, 3, 0, 1, 2}

	quickSort(nums)

	assert.Equal(t, []int{0, 1, 2, 3, 4}, nums)
}
