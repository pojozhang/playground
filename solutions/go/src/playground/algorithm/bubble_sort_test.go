package algorithm

import (
	"github.com/stretchr/testify/assert"
	"testing"
)

func TestBubbleSortCase1(t *testing.T) {
	nums := []int{3, 4, 5, 2, 1}

	bubbleSort(nums)

	assert.Equal(t, []int{1, 2, 3, 4, 5}, nums)
}
