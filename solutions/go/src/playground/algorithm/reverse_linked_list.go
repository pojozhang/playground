package algorithm

func reverseList(head *ListNode) *ListNode {
	return reverseListR(nil, head)
}

func reverseListR(previous *ListNode, current *ListNode) *ListNode {
	if current == nil {
		return previous
	}
	head := reverseListR(current, current.Next)
	current.Next = previous
	return head
}
