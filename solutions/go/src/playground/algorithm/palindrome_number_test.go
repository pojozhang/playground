package algorithm

import (
	"github.com/stretchr/testify/assert"
	"testing"
)

func TestIsPalindromeCase1(t *testing.T) {
	assert.False(t, isPalindrome(-121))
}

func TestIsPalindromeCase2(t *testing.T) {
	assert.True(t, isPalindrome(121))
}

func TestIsPalindromeCase3(t *testing.T) {
	assert.True(t, isPalindrome(1))
}

func TestIsPalindromeCase4(t *testing.T) {
	assert.True(t, isPalindrome(1000000001))
}
