package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestMinStackCase1(t *testing.T) {
	stack := Constructor()
	stack.Push(1)

	assert.Equal(t, 1, stack.GetMin())

	stack.Push(2)
	assert.Equal(t, 1, stack.GetMin())

	stack.Pop()
	assert.Equal(t, 1, stack.GetMin())
}
