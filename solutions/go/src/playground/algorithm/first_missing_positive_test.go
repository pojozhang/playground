package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestFirstMissingPositiveCase1(t *testing.T) {
	assert.Equal(t, 3, firstMissingPositive([]int{1, 2, 0}))
}

func TestFirstMissingPositiveCase2(t *testing.T) {
	assert.Equal(t, 2, firstMissingPositive([]int{3, 4, -1, 1}))
}

func TestFirstMissingPositiveCase3(t *testing.T) {
	assert.Equal(t, 1, firstMissingPositive([]int{7, 8, 9, 11, 12}))
}
