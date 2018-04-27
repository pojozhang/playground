package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestRomanToIntCase1(t *testing.T) {
	assert.Equal(t, 3, romanToInt("III"))
}

func TestRomanToIntCase2(t *testing.T) {
	assert.Equal(t, 4, romanToInt("IV"))
}

func TestRomanToIntCase3(t *testing.T) {
	assert.Equal(t, 9, romanToInt("IX"))
}

func TestRomanToIntCase4(t *testing.T) {
	assert.Equal(t, 58, romanToInt("LVIII"))
}

func TestRomanToIntCase5(t *testing.T) {
	assert.Equal(t, 1994, romanToInt("MCMXCIV"))
}