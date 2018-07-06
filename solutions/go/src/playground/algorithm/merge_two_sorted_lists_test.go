package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestMergeTwoLists(t *testing.T) {
	l1 := &ListNode{Val: -9}
	l1.Next = &ListNode{Val: 3}
	l2 := &ListNode{Val: 5}
	l2.Next = &ListNode{Val: 7}

	merged := mergeTwoLists(l1, l2)
	assert.Equal(t, -9, merged.Val)
	assert.Equal(t, 3, merged.Next.Val)
	assert.Equal(t, 5, merged.Next.Next.Val)
	assert.Equal(t, 7, merged.Next.Next.Next.Val)
	assert.Nil(t, merged.Next.Next.Next.Next)
}
