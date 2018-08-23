package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestLetterCombinationsCase1(t *testing.T) {
	result := letterCombinations("")

	assert.Empty(t, result)
}

func TestLetterCombinationsCase2(t *testing.T) {
	result := letterCombinations("23")

	assert.Len(t, result, 9)
	assert.Contains(t, result, "ad")
	assert.Contains(t, result, "ae")
	assert.Contains(t, result, "af")
	assert.Contains(t, result, "bd")
	assert.Contains(t, result, "be")
	assert.Contains(t, result, "bf")
	assert.Contains(t, result, "cd")
	assert.Contains(t, result, "ce")
	assert.Contains(t, result, "cf")
}
