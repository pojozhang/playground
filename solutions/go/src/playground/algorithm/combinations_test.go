package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestCombineCase1(t *testing.T) {
	expected := [][]int{
		{2, 4},
		{3, 4},
		{2, 3},
		{1, 2},
		{1, 3},
		{1, 4},
	}

	assert.ElementsMatch(t, expected, combine(4, 2))
}
