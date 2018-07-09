package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestSpiralOrderCase1(t *testing.T) {
	actual := spiralOrder([][]int{
		{1, 2, 3},
		{4, 5, 6},
		{7, 8, 9},
	})

	assert.Equal(t, []int{1, 2, 3, 6, 9, 8, 7, 4, 5}, actual)
}

func TestSpiralOrderCase2(t *testing.T) {
	actual := spiralOrder([][]int{
		{6, 9, 7},
	})

	assert.Equal(t, []int{6, 9, 7}, actual)
}
