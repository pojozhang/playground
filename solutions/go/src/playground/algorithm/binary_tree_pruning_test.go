package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestPruneTreeCase1(t *testing.T) {
	root := &TreeNode{Val: 1}
	root.Right = &TreeNode{Val: 0}
	root.Right.Left = &TreeNode{Val: 0}
	root.Right.Right = &TreeNode{Val: 1}

	root = pruneTree(root)

	assert.Nil(t, root.Right.Left)
}

func TestPruneTreeCase2(t *testing.T) {
	root := &TreeNode{Val: 1}
	root.Left = &TreeNode{Val: 1}
	root.Right = &TreeNode{Val: 0}
	root.Left.Left = &TreeNode{Val: 1}
	root.Left.Right = &TreeNode{Val: 1}
	root.Right.Left = &TreeNode{Val: 0}
	root.Right.Right = &TreeNode{Val: 1}
	root.Left.Left.Left = &TreeNode{Val: 0}

	root = pruneTree(root)

	assert.Nil(t, root.Left.Left.Left)
	assert.Nil(t, root.Right.Left)
}

func TestPruneTreeCase3(t *testing.T) {
	root := &TreeNode{Val: 1}
	root.Left = &TreeNode{Val: 1}
	root.Right = &TreeNode{Val: 0}
	root.Left.Left = &TreeNode{Val: 1}
	root.Left.Right = &TreeNode{Val: 0}
	root.Left.Right.Left = &TreeNode{Val: 0}
	root.Right.Left = &TreeNode{Val: 0}
	root.Right.Right = &TreeNode{Val: 1}
	root.Right.Left.Right = &TreeNode{Val: 1}

	root = pruneTree(root)

	assert.Nil(t, root.Left.Right)
}
