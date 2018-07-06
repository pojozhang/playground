package algorithm

func addTwoNumbers(l1 *ListNode, l2 *ListNode) *ListNode {
	head := new(ListNode)
	p := head

	for carry := 0; l1 != nil || l2 != nil || carry > 0; {
		v1 := 0
		if l1 != nil {
			v1 = l1.Val
			l1 = l1.Next
		}

		v2 := 0
		if l2 != nil {
			v2 = l2.Val
			l2 = l2.Next
		}

		v := v1 + v2 + carry
		carry = v / 10

		p.Next = &ListNode{Val: v % 10}
		p = p.Next
	}

	return head.Next
}
