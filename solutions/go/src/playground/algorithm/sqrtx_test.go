package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestMySqrtCase1(t *testing.T) {
	assert.Equal(t, 2, mySqrt(4))
}

func TestMySqrtCase2(t *testing.T) {
	assert.Equal(t, 2, mySqrt(8))
}

func TestMySqrtCase3(t *testing.T) {
	assert.Equal(t, 1, mySqrt(1))
}
