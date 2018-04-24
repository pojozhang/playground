package algorithm

import (
	"github.com/stretchr/testify/assert"
	"testing"
)

func TestLongestCommonPrefixCase1(t *testing.T) {
	assert.Equal(t, "fl", longestCommonPrefix([]string{"flower", "flow", "flight"}))
}

func TestLongestCommonPrefixCase2(t *testing.T) {
	assert.Equal(t, "", longestCommonPrefix([]string{"dog", "racecar", "car"}))
}

func TestLongestCommonPrefixCase3(t *testing.T) {
	assert.Equal(t, "a", longestCommonPrefix([]string{"a"}))
}

func TestLongestCommonPrefixCase4(t *testing.T) {
	assert.Equal(t, "", longestCommonPrefix([]string{}))
}
