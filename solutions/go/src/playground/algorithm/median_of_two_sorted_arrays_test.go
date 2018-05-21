package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestFindMedianSortedArraysCase1(t *testing.T) {
	assert.Equal(t, 2.0, findMedianSortedArrays([]int{1, 3}, []int{2}))
}

func TestFindMedianSortedArraysCase2(t *testing.T) {
	assert.Equal(t, 2.5, findMedianSortedArrays([]int{1, 2}, []int{3, 4}))
}
