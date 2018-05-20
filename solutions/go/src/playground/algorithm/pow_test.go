package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestMyPowCase1(t *testing.T) {
	assert.Equal(t, 1024.00000, myPow(2.00000, 10))
}

func TestMyPowCase2(t *testing.T) {
	assert.InDelta(t, 9.26100, myPow(2.10000, 3), 0.00001)
}

func TestMyPowCase3(t *testing.T) {
	assert.Equal(t, 0.25, myPow(2.00000, -2))
}
