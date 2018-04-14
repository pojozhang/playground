package algorithm

import (
	"github.com/stretchr/testify/assert"
	"testing"
)

func TestAddTwoNumbersCase1(t *testing.T) {
	l1 := &listNode1{Val: 2}
	l1.Next = &listNode1{Val: 4}
	l1.Next.Next = &listNode1{Val: 3}
	l2 := &listNode1{Val: 5}
	l2.Next = &listNode1{Val: 6}
	l2.Next.Next = &listNode1{Val: 4}

	head := addTwoNumbers(l1, l2)

	assert.Equal(t, 7, head.Val)
	assert.Equal(t, 0, head.Next.Val)
	assert.Equal(t, 8, head.Next.Next.Val)
}

func TestAddTwoNumbersCase2(t *testing.T) {
	l1 := &listNode1{Val: 5}
	l2 := &listNode1{Val: 5}

	head := addTwoNumbers(l1, l2)

	assert.Equal(t, 0, head.Val)
	assert.Equal(t, 1, head.Next.Val)
}
