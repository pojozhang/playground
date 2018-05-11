package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestFourSumCase1(t *testing.T) {
	result := fourSum([]int{1, 0, -1, 0, -2, 2}, 0)

	assert.Len(t, result, 3)
	assert.Contains(t, result, []int{-1, 0, 0, 1})
	assert.Contains(t, result, []int{-2, -1, 1, 2})
	assert.Contains(t, result, []int{-2, 0, 0, 2})
}

func TestFourSumCase2(t *testing.T) {
	result := fourSum([]int{0, 0, 0, 0}, 0)

	assert.Len(t, result, 1)
	assert.Contains(t, result, []int{0, 0, 0, 0})
}
