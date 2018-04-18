package algorithm

import (
	"github.com/stretchr/testify/assert"
	"testing"
)

func TestReverseIntegerCase1(t *testing.T) {
	assert.Equal(t, 21, reverseInteger(120))
}

func TestReverseIntegerCase2(t *testing.T) {
	assert.Equal(t, 321, reverseInteger(123))
}

func TestReverseIntegerCase3(t *testing.T) {
	assert.Equal(t, -321, reverseInteger(-123))
}

func TestReverseIntegerCase4(t *testing.T) {
	assert.Equal(t, 0, reverseInteger(-2147483648))
}

func TestReverseIntegerCase5(t *testing.T) {
	assert.Equal(t, 0, reverseInteger(1534236469))
}
