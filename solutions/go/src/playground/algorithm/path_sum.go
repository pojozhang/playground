package algorithm

func hasPathSum(root *TreeNode, sum int) bool {
	return hasPathSumR(root, 0, sum)
}

func hasPathSumR(node *TreeNode, sum, target int) bool {
	if node == nil {
		return false
	}

	if sum += node.Val; sum == target && node.Left == nil && node.Right == nil {
		return true
	}

	return hasPathSumR(node.Left, sum, target) || hasPathSumR(node.Right, sum, target)
}
