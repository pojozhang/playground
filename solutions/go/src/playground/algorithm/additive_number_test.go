package algorithm

import (
	"github.com/stretchr/testify/assert"
	"testing"
)

func TestIsAdditiveNumberCase1(t *testing.T) {
	assert.True(t, isAdditiveNumber("112358"))
}

func TestIsAdditiveNumberCase2(t *testing.T) {
	assert.True(t, isAdditiveNumber("199100199"))
}

func TestIsAdditiveNumberCase3(t *testing.T) {
	assert.False(t, isAdditiveNumber("1023"))
}

func TestIsAdditiveNumberCase4(t *testing.T) {
	assert.True(t, isAdditiveNumber("101"))
}

func TestIsAdditiveNumberCase5(t *testing.T) {
	assert.False(t, isAdditiveNumber("0235813"))
}
