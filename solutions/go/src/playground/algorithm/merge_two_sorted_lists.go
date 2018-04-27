package algorithm

type listNode2 struct {
	Val  int
	Next *listNode2
}

func mergeTwoLists(l1 *listNode2, l2 *listNode2) *listNode2 {
	root := &listNode2{}
	it := root

	for l1 != nil && l2 != nil {
		if l1.Val <= l2.Val {
			it.Next = &listNode2{Val: l1.Val}
			l1 = l1.Next
		} else {
			it.Next = &listNode2{Val: l2.Val}
			l2 = l2.Next
		}
		it = it.Next
	}

	for l1 != nil {
		it.Next = &listNode2{Val: l1.Val}
		l1 = l1.Next
		it = it.Next
	}

	for l2 != nil {
		it.Next = &listNode2{Val: l2.Val}
		l2 = l2.Next
		it = it.Next
	}

	return root.Next
}
