package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestIsValidCase1(t *testing.T) {
	assert.True(t, isValid("()"))
}

func TestIsValidCase2(t *testing.T) {
	assert.False(t, isValid("]"))
}
