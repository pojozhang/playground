package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestMaxArea(t *testing.T) {
	assert.Equal(t, 2, maxArea([]int{1, 3, 2}))
}
