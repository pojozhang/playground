package algorithm

import (
	"github.com/stretchr/testify/assert"
	"testing"
)

func TestLongestPalindromeCase1(t *testing.T) {
	assert.Equal(t, "bab", longestPalindrome("babad"))
}

func TestLongestPalindromeCase2(t *testing.T) {
	assert.Equal(t, "bb", longestPalindrome("bb"))
}
