package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestDivideCase1(t *testing.T) {
	assert.Equal(t, 3, divide(10, 3))
}

func TestDivideCase2(t *testing.T) {
	assert.Equal(t, -2, divide(7, -3))
}

func TestDivideCase3(t *testing.T) {
	assert.Equal(t, 1, divide(1, 1))
}

func TestDivideCase4(t *testing.T) {
	assert.Equal(t, -2147483648, divide(-2147483648, 1))
}
