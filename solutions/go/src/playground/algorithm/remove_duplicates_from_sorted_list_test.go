package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestDeleteDuplicatesCase1(t *testing.T) {
	head := &ListNode{Val: 1}
	head.Next = &ListNode{Val: 1}
	head.Next.Next = &ListNode{Val: 2}

	h := deleteDuplicates(head)

	assert.Equal(t, 1, h.Val)
	assert.Equal(t, 2, h.Next.Val)
	assert.Nil(t, h.Next.Next)
}

func TestDeleteDuplicatesCase2(t *testing.T) {
	head := &ListNode{Val: 1}
	head.Next = &ListNode{Val: 1}
	head.Next.Next = &ListNode{Val: 2}
	head.Next.Next.Next = &ListNode{Val: 3}
	head.Next.Next.Next.Next = &ListNode{Val: 3}

	h := deleteDuplicates(head)

	assert.Equal(t, 1, h.Val)
	assert.Equal(t, 2, h.Next.Val)
	assert.Equal(t, 3, h.Next.Next.Val)
	assert.Nil(t, h.Next.Next.Next)
}
