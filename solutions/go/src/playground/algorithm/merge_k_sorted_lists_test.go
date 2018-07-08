package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestMergeKListsCase1(t *testing.T) {
	lists := make([]*ListNode, 3)

	lists[0] = &ListNode{Val: 1}
	lists[0].Next = &ListNode{Val: 4}
	lists[0].Next.Next = &ListNode{Val: 5}

	lists[1] = &ListNode{Val: 1}
	lists[1].Next = &ListNode{Val: 3}
	lists[1].Next.Next = &ListNode{Val: 4}

	lists[2] = &ListNode{Val: 2}
	lists[2].Next = &ListNode{Val: 6}

	merged := mergeKLists(lists)

	assert.Equal(t, 1, merged.Val)
	assert.Equal(t, 1, merged.Next.Val)
	assert.Equal(t, 2, merged.Next.Next.Val)
	assert.Equal(t, 3, merged.Next.Next.Next.Val)
	assert.Equal(t, 4, merged.Next.Next.Next.Next.Val)
	assert.Equal(t, 4, merged.Next.Next.Next.Next.Next.Val)
	assert.Equal(t, 5, merged.Next.Next.Next.Next.Next.Next.Val)
	assert.Equal(t, 6, merged.Next.Next.Next.Next.Next.Next.Next.Val)
	assert.Nil(t, merged.Next.Next.Next.Next.Next.Next.Next.Next)
}

func TestMergeKListsCase2(t *testing.T) {
	lists := make([]*ListNode, 1)

	merged := mergeKLists(lists)

	assert.Nil(t, merged)
}
