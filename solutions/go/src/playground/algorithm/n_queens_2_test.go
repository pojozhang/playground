package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestTotalNQueensCase1(t *testing.T) {
	assert.Equal(t, 2, totalNQueens(4))
}
