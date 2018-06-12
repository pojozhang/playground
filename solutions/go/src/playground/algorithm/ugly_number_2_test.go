package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestNthUglyNumberCase1(t *testing.T) {
	assert.Equal(t, 12, nthUglyNumber(10))
}
