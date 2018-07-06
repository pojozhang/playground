package algorithm

func mergeTwoLists(l1 *ListNode, l2 *ListNode) *ListNode {
	root := &ListNode{}
	it := root

	for l1 != nil && l2 != nil {
		if l1.Val <= l2.Val {
			it.Next = &ListNode{Val: l1.Val}
			l1 = l1.Next
		} else {
			it.Next = &ListNode{Val: l2.Val}
			l2 = l2.Next
		}
		it = it.Next
	}

	for l1 != nil {
		it.Next = &ListNode{Val: l1.Val}
		l1 = l1.Next
		it = it.Next
	}

	for l2 != nil {
		it.Next = &ListNode{Val: l2.Val}
		l2 = l2.Next
		it = it.Next
	}

	return root.Next
}
