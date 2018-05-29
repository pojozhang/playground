package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestIsUglyCase1(t *testing.T) {
	assert.True(t, isUgly(6))
}

func TestIsUglyCase2(t *testing.T) {
	assert.True(t, isUgly(8))
}

func TestIsUglyCase3(t *testing.T) {
	assert.False(t, isUgly(14))
}

func TestIsUglyCase4(t *testing.T) {
	assert.False(t, isUgly(0))
}
