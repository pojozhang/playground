package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestCombinationSumCase1(t *testing.T) {
	result := combinationSum([]int{2, 3, 6, 7}, 7)

	assert.Len(t, result, 2)
	assert.Contains(t, result, []int{7})
	assert.Contains(t, result, []int{2, 2, 3})
}

func TestCombinationSumCase2(t *testing.T) {
	result := combinationSum([]int{2, 3, 5}, 8)

	assert.Len(t, result, 3)
	assert.Contains(t, result, []int{2, 2, 2, 2})
	assert.Contains(t, result, []int{2, 3, 3})
	assert.Contains(t, result, []int{3, 5})
}

func TestCombinationSumCase3(t *testing.T) {
	a := make([]int, 32)
	b := a[1:16]
	a = append(a, 1)
	a[2] = 42

	println(a[2])
	println(b[2])
}
