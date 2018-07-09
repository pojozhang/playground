package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestCanJumpCase1(t *testing.T) {
	assert.True(t, canJump([]int{2, 3, 1, 1, 4}))
}

func TestCanJumpCase2(t *testing.T) {
	assert.False(t, canJump([]int{3, 2, 1, 0, 4}))
}
