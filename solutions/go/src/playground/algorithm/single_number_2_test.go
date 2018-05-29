package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestSingleNumber2Case1(t *testing.T) {
	assert.Equal(t, 3, singleNumber2([]int{2, 2, 3, 2}))
}

func TestSingleNumber2Case2(t *testing.T) {
	assert.Equal(t, 99, singleNumber2([]int{0, 1, 0, 1, 0, 1, 99}))
}

func TestSingleNumber2Case3(t *testing.T) {
	assert.Equal(t, -4, singleNumber2([]int{-2, -2, 1, 1, -3, 1, -3, -3, -4, -2}))
}
