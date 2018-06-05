package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestSolveNQueensCase1(t *testing.T) {
	result := solveNQueens(4)

	assert.Len(t, result, 2)
	assert.Contains(t, result,
		[]string{
			".Q..",
			"...Q",
			"Q...",
			"..Q."},
		[]string{
			"..Q.",
			"Q...",
			"...Q",
			".Q.."})
}
