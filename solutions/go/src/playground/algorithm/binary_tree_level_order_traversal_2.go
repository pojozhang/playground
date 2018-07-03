package algorithm

func levelOrderBottom(root *TreeNode) [][]int {
	result := make([][]int, 0)
	if root == nil {
		return result
	}

	l := 0
	for queue := []*TreeNode{root}; len(queue) > 0; queue = queue[l:] {
		l = len(queue)
		r := make([]int, 0, l)
		for i := 0; i < l; i++ {
			n := queue[i]
			r = append(r, n.Val)
			if n.Left != nil {
				queue = append(queue, n.Left)
			}
			if n.Right != nil {
				queue = append(queue, n.Right)
			}
		}
		result = append(result, r)
	}

	// reverse result
	for l, i := len(result)-1, 0; i <= len(result)/2-1; i++ {
		result[i], result[l-i] = result[l-i], result[i]
	}

	return result
}
