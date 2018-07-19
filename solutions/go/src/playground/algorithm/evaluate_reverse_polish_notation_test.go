package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestEvalRPNCase1(t *testing.T) {
	assert.Equal(t, 9, evalRPN([]string{"2", "1", "+", "3", "*"}))
}

func TestEvalRPNCase2(t *testing.T) {
	assert.Equal(t, 6, evalRPN([]string{"4", "13", "5", "/", "+"}))
}

func TestEvalRPNCase3(t *testing.T) {
	assert.Equal(t, 22, evalRPN([]string{"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"}))
}
