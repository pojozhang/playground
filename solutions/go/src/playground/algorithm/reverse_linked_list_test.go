package algorithm

import (
	"github.com/stretchr/testify/assert"
	"testing"
)

func TestReverseListCase1(t *testing.T) {
	head := &ListNode{Val: 1}
	head.Next = &ListNode{Val: 2}
	head.Next.Next = &ListNode{Val: 3}
	head.Next.Next.Next = &ListNode{Val: 4}
	head.Next.Next.Next.Next = &ListNode{Val: 5}

	reversed := reverseList(head)

	assert.Equal(t, 5, reversed.Val)
	assert.Equal(t, 4, reversed.Next.Val)
	assert.Equal(t, 3, reversed.Next.Next.Val)
	assert.Equal(t, 2, reversed.Next.Next.Next.Val)
	assert.Equal(t, 1, reversed.Next.Next.Next.Next.Val)
	assert.Nil(t, reversed.Next.Next.Next.Next.Next)
}
