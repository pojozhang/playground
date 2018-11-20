package algorithm

import (
	"github.com/stretchr/testify/assert"
	"testing"
)

func TestKthSmallestCase1(t *testing.T) {
	root := &TreeNode{Val: 3}
	root.Left = &TreeNode{Val: 1}
	root.Right = &TreeNode{Val: 4}
	root.Left.Right = &TreeNode{Val: 2}

	assert.Equal(t, 1, kthSmallest(root, 1))
}

func TestKthSmallestCase2(t *testing.T) {
	root := &TreeNode{Val: 5}
	root.Left = &TreeNode{Val: 3}
	root.Right = &TreeNode{Val: 6}
	root.Left.Left = &TreeNode{Val: 2}
	root.Left.Right = &TreeNode{Val: 4}
	root.Left.Left.Left = &TreeNode{Val: 1}

	assert.Equal(t, 3, kthSmallest(root, 3))
}
