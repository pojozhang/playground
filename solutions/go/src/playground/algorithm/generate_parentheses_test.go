package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestGenerateParenthesisCase1(t *testing.T) {
	result := generateParenthesis(3)

	assert.Len(t, result, 5)
	assert.Contains(t, result, "((()))")
	assert.Contains(t, result, "(()())")
	assert.Contains(t, result, "(())()")
	assert.Contains(t, result, "()(())")
	assert.Contains(t, result, "()()()")
}
