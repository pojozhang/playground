package algorithm

func pruneTree(root *TreeNode) *TreeNode {
	if needPrune(root) {
		return nil
	}

	if root.Left != nil {
		root.Left = pruneTree(root.Left)
	}
	if root.Right != nil {
		root.Right = pruneTree(root.Right)
	}
	return root
}

func needPrune(node *TreeNode) bool {
	if node.Val == 1 {
		return false
	}

	if node.Left != nil && !needPrune(node.Left) || node.Right != nil && !needPrune(node.Right) {
		return false
	}

	return true
}
