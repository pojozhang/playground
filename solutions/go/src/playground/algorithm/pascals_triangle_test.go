package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestGenerateCase1(t *testing.T) {
	result := generate(5)

	assert.Equal(t, [][]int{
		{1},
		{1, 1},
		{1, 2, 1},
		{1, 3, 3, 1},
		{1, 4, 6, 4, 1},
	}, result)
}
