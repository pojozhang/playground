package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestTwoSum(t *testing.T) {
	nums := []int{1, 5, 5, 20}
	target := 10

	assert.Equal(t, []int{1, 2}, twoSum(nums, target))
}
