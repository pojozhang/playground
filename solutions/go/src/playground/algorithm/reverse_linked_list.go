package algorithm

// 递归解法
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

// 循环解法
func reverseList2(head *ListNode) *ListNode {
	if head == nil {
		return nil
	}

	var previous, current, next *ListNode
	for current, next = head, head.Next; current != nil && next != nil; previous, current, next = current, next, next.Next {
		current.Next = previous
	}
	current.Next = previous
	return current
}
