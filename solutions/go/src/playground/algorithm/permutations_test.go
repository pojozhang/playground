package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestPermute(t *testing.T) {
	expected := [][]int{
		{1, 2, 3},
		{1, 3, 2},
		{2, 1, 3},
		{2, 3, 1},
		{3, 2, 1},
		{3, 1, 2},
	}
	assert.Equal(t, expected, permute([]int{1, 2, 3}))
}
