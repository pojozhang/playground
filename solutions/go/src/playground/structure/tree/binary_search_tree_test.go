package tree

import (
	"github.com/stretchr/testify/assert"
	. "playground/structure"
	"testing"
)

func TestBinarySearchTree_Add(t *testing.T) {
	tree := NewBinarySearchTree()
	tree.Add(Integer(12))
	tree.Add(Integer(5))
	tree.Add(Integer(18))
	tree.Add(Integer(2))
	tree.Add(Integer(9))
	tree.Add(Integer(15))
	tree.Add(Integer(19))

	assert.Equal(t, Integer(12), tree.root.value)
	assert.Equal(t, Integer(5), tree.root.left.value)
	assert.Equal(t, Integer(18), tree.root.right.value)
	assert.Equal(t, Integer(2), tree.root.left.left.value)
	assert.Equal(t, Integer(9), tree.root.left.right.value)
	assert.Equal(t, Integer(15), tree.root.right.left.value)
	assert.Equal(t, Integer(19), tree.root.right.right.value)
}

func TestBinarySearchTree_Remove_1(t *testing.T) {
	tree := NewBinarySearchTree()
	tree.Add(Integer(1))
	tree.Add(Integer(3))
	tree.Add(Integer(2))

	tree.Remove(Integer(1))

	assert.Equal(t, Integer(3), tree.root.value)
	assert.Equal(t, Integer(2), tree.root.left.value)
}

func TestBinarySearchTree_Remove_2(t *testing.T) {
	tree := NewBinarySearchTree()
	tree.Add(Integer(6))
	tree.Add(Integer(2))
	tree.Add(Integer(10))
	tree.Add(Integer(12))

	tree.Remove(Integer(6))

	assert.Nil(t, tree.root.parent)
	assert.Equal(t, Integer(10), tree.root.value)
	assert.Equal(t, Integer(2), tree.root.left.value)
	assert.Equal(t, Integer(12), tree.root.right.value)
}

func TestBinarySearchTree_Remove_3(t *testing.T) {
	tree := NewBinarySearchTree()
	tree.Add(Integer(20))
	tree.Add(Integer(10))
	tree.Add(Integer(40))
	tree.Add(Integer(30))
	tree.Add(Integer(50))
	tree.Add(Integer(25))
	tree.Add(Integer(27))

	tree.Remove(Integer(20))

	assert.Equal(t, Integer(25), tree.root.value)
	assert.Equal(t, Integer(10), tree.root.left.value)
	assert.Equal(t, Integer(40), tree.root.right.value)
	assert.Equal(t, Integer(30), tree.root.right.left.value)
	assert.Equal(t, Integer(50), tree.root.right.right.value)
	assert.Equal(t, Integer(27), tree.root.right.left.left.value)
}
