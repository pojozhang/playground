package algorithm

type listNode1 struct {
	Val  int
	Next *listNode1
}

func addTwoNumbers(l1 *listNode1, l2 *listNode1) *listNode1 {
	head := new(listNode1)
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

		p.Next = &listNode1{Val: v % 10}
		p = p.Next
	}

	return head.Next
}
