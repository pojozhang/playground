package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestCompareVersionCase1(t *testing.T) {
	assert.Equal(t, -1, compareVersion("0.1", "1.1"))
}

func TestCompareVersionCase2(t *testing.T) {
	assert.Equal(t, 1, compareVersion("1.0.1", "1"))
}

func TestCompareVersionCase3(t *testing.T) {
	assert.Equal(t, -1, compareVersion("7.5.2.4", "7.5.3"))
}

func TestCompareVersionCase4(t *testing.T) {
	assert.Equal(t, 0, compareVersion("1.0", "1"))
}

func TestCompareVersionCase5(t *testing.T) {
	assert.Equal(t, -1, compareVersion("1", "1.1"))
}
