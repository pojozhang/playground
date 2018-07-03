package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestLevelOrderBottomCase1(t *testing.T) {
	root := &TreeNode{Val: 3}
	root.Left = &TreeNode{Val: 9}
	root.Right = &TreeNode{Val: 20}
	root.Right.Left = &TreeNode{Val: 15}
	root.Right.Right = &TreeNode{Val: 7}

	assert.Equal(t,
		[][]int{
			{15, 7},
			{9, 20},
			{3},
		}, levelOrderBottom(root))
}

func TestLevelOrderBottomCase2(t *testing.T) {
	assert.Equal(t, [][]int{}, levelOrderBottom(nil))
}

func TestLevelOrderBottomCase3(t *testing.T) {
	root := &TreeNode{Val: 1}
	root.Left = &TreeNode{Val: 2}
	root.Left.Left = &TreeNode{Val: 3}
	root.Left.Left.Left = &TreeNode{Val: 4}
	root.Left.Left.Left.Left = &TreeNode{Val: 5}

	assert.Equal(t,
		[][]int{
			{5},
			{4},
			{3},
			{2},
			{1},
		}, levelOrderBottom(root))
}
