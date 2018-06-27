package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestHasPathSumCase1(t *testing.T) {
	root := &TreeNode{Val: 5}
	root.Left = &TreeNode{Val: 4}
	root.Left.Left = &TreeNode{Val: 11}
	root.Left.Left.Left = &TreeNode{Val: 7}
	root.Left.Left.Right = &TreeNode{Val: 2}
	root.Right = &TreeNode{Val: 8}
	root.Right.Left = &TreeNode{Val: 13}
	root.Right.Right = &TreeNode{Val: 4}
	root.Right.Right.Right = &TreeNode{Val: 1}

	assert.True(t, hasPathSum(root, 22))
}

func TestHasPathSumCase2(t *testing.T) {
	root := &TreeNode{Val: 1}
	root.Left = &TreeNode{Val: 2}

	assert.False(t, hasPathSum(root, 1))
}

func TestHasPathSumCase3(t *testing.T) {
	assert.False(t, hasPathSum(nil, 1))
}

func TestHasPathSumCase4(t *testing.T) {
	root := &TreeNode{Val: 1}

	assert.True(t, hasPathSum(root, 1))
}

func TestHasPathSumCase5(t *testing.T) {
	root := &TreeNode{Val: 1}
	root.Left = &TreeNode{Val: 2}
	root.Left.Left = &TreeNode{Val: 3}
	root.Left.Left.Left = &TreeNode{Val: 4}
	root.Left.Left.Left.Left = &TreeNode{Val: 5}

	assert.False(t, hasPathSum(root, 6))
}
