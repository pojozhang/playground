package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestSearchMatrixCase1(t *testing.T) {
	assert.True(t, searchMatrix([][]int{{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 50}}, 3))
}

func TestSearchMatrixCase2(t *testing.T) {
	assert.False(t, searchMatrix([][]int{{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 50}}, 13))
}

func TestSearchMatrixCase3(t *testing.T) {
	assert.False(t, searchMatrix([][]int{}, 0))
}
