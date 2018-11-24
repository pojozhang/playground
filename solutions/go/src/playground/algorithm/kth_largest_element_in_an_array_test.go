package algorithm

import (
	"github.com/stretchr/testify/assert"
	"testing"
)

func TestFindKthLargestCase1(t *testing.T) {
	assert.Equal(t, 5, findKthLargest([]int{3, 2, 1, 5, 6, 4}, 2))
}

func TestFindKthLargestCase2(t *testing.T) {
	assert.Equal(t, 4, findKthLargest([]int{3, 2, 3, 1, 2, 4, 5, 5, 6}, 4))
}

func TestFindKthLargestCase3(t *testing.T) {
	assert.Equal(t, 1, findKthLargest([]int{2, 1}, 2))
}
