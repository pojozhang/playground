package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestLargestRectangleAreaCase1(t *testing.T) {
	assert.Equal(t, 10, largestRectangleArea([]int{2, 1, 5, 6, 2, 3}))
}

func TestLargestRectangleAreaCase2(t *testing.T) {
	assert.Equal(t, 1, largestRectangleArea([]int{1}))
}

func TestLargestRectangleAreaCase3(t *testing.T) {
	assert.Equal(t, 2, largestRectangleArea([]int{1, 1}))
}

func TestLargestRectangleAreaCase4(t *testing.T) {
	assert.Equal(t, 3, largestRectangleArea([]int{2, 1, 2}))
}

func TestLargestRectangleAreaCase5(t *testing.T) {
	assert.Equal(t, 9, largestRectangleArea([]int{1, 2, 3, 4, 5}))
}
