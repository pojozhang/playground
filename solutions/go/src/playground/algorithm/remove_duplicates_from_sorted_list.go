package algorithm

func deleteDuplicates(head *ListNode) *ListNode {
	if head == nil {
		return nil
	}

	list := &ListNode{Val: head.Val}
	last := list
	for head = head.Next; head != nil; head = head.Next {
		if head.Val == last.Val {
			continue
		}
		last.Next = &ListNode{Val: head.Val}
		last = last.Next
	}
	return list
}
