package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestSingleNumberCase1(t *testing.T) {
	assert.Equal(t, 1, singleNumber([]int{2, 2, 1}))
}

func TestSingleNumberCase2(t *testing.T) {
	assert.Equal(t, 4, singleNumber([]int{4, 1, 2, 1, 2}))
}
