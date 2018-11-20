package algorithm

func kthSmallest(root *TreeNode, k int) int {
	current := 0
	result, _ := kthSmallestR(root, &current, k)
	return result
}

func kthSmallestR(node *TreeNode, current *int, target int) (value int, found bool) {
	if node == nil {
		return 0, false
	}

	if left, ok := kthSmallestR(node.Left, current, target); ok {
		return left, true
	}

	if *current = *current + 1; *current == target {
		return node.Val, true
	}

	if right, ok := kthSmallestR(node.Right, current, target); ok {
		return right, true
	}

	return 0, false
}
