package algorithm

func invertTree(root *TreeNode) *TreeNode {
	if root == nil {
		return nil
	}

	inverted := &TreeNode{Val: root.Val}
	invertTreeR(root, inverted)
	return inverted
}

func invertTreeR(src *TreeNode, dest *TreeNode) {
	if src.Left != nil {
		dest.Right = &TreeNode{Val: src.Left.Val}
		invertTreeR(src.Left, dest.Right)
	}

	if src.Right != nil {
		dest.Left = &TreeNode{Val: src.Right.Val}
		invertTreeR(src.Right, dest.Left)
	}
}
