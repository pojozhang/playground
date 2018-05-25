package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestMoveZeroesCase1(t *testing.T) {
	nums := []int{0, 1, 0, 3, 12}

	moveZeroes(nums)

	assert.Equal(t, []int{1, 3, 12, 0, 0}, nums)
}
