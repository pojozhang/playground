package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestIsPowerOfTwoCase1(t *testing.T) {
	assert.True(t, isPowerOfTwo(1))
}

func TestIsPowerOfTwoCase2(t *testing.T) {
	assert.True(t, isPowerOfTwo(16))
}

func TestIsPowerOfTwoCase3(t *testing.T) {
	assert.False(t, isPowerOfTwo(218))
}

func TestIsPowerOfTwoCase4(t *testing.T) {
	assert.False(t, isPowerOfTwo(0))
}
