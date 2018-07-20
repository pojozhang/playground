package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestSortColorsCase1(t *testing.T) {
	nums := []int{2, 0, 2, 1, 1, 0}

	sortColors(nums)

	assert.Equal(t, []int{0, 0, 1, 1, 2, 2}, nums)
}

func TestSortColorsCase2(t *testing.T) {
	nums := []int{2, 0, 1}

	sortColors(nums)

	assert.Equal(t, []int{0, 1, 2}, nums)
}
