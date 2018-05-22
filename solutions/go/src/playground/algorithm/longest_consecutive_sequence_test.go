package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestLongestConsecutiveCase1(t *testing.T) {
	assert.Equal(t, 4, longestConsecutive([]int{100, 4, 200, 1, 3, 2}))
}

func TestLongestConsecutiveCase2(t *testing.T) {
	assert.Equal(t, 3, longestConsecutive([]int{1, 2, 0, 1}))
}
