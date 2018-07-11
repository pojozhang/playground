package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestJumpCase1(t *testing.T) {
	assert.Equal(t, 2, jump([]int{2, 3, 1, 1, 4}))
}
