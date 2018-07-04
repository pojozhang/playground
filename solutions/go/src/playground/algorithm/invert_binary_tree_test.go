package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestInvertTreeCase1(t *testing.T) {
	root := &TreeNode{Val: 4}
	root.Left = &TreeNode{Val: 2}
	root.Right = &TreeNode{Val: 7}
	root.Left.Left = &TreeNode{Val: 1}
	root.Left.Right = &TreeNode{Val: 3}
	root.Right.Left = &TreeNode{Val: 6}
	root.Right.Right = &TreeNode{Val: 9}

	inverted := invertTree(root)

	assert.Equal(t, root.Val, inverted.Val)
	assert.Equal(t, root.Right.Val, inverted.Left.Val)
	assert.Equal(t, root.Left.Val, inverted.Right.Val)
	assert.Equal(t, root.Right.Right.Val, inverted.Left.Left.Val)
	assert.Equal(t, root.Right.Left.Val, inverted.Left.Right.Val)
	assert.Equal(t, root.Left.Right.Val, inverted.Right.Left.Val)
	assert.Equal(t, root.Left.Left.Val, inverted.Right.Right.Val)
}
