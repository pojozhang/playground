package tree

import . "playground/structure"

type RedBlackTreeNode struct {
	value  Comparable
	color  color
	left   *RedBlackTreeNode
	right  *RedBlackTreeNode
	parent *RedBlackTreeNode
}

type color uint8

const (
	RED color = iota
	BLACK
)

type RedBlackTree struct {
	root *RedBlackTreeNode
}

/*      p                       p
		|                       |
		x (node)                y
       / \                     / \
	  a   y       ===>        x   c
         / \                 / \
        b   c               a   b
*/
func (t *RedBlackTree) leftRotate(node *RedBlackTreeNode) {
	rightSubTree := node.right
	node.right = rightSubTree.left
	if rightSubTree.left != nil {
		rightSubTree.left.parent = node
	}
	rightSubTree.parent = node.parent
	if rightSubTree.parent == nil {
		t.root = rightSubTree
	} else if rightSubTree.parent.left == node {
		rightSubTree.parent.left = rightSubTree
	} else {
		rightSubTree.parent.right = rightSubTree
	}
	rightSubTree.left = node
	node.parent = rightSubTree
}

/*        p                       p
		  |                       |
		  x (node)                y
         / \                     / \
	    y   c       ===>        a   x
       / \                         / \
      a   b                       b   c
*/
func (t *RedBlackTree) rightRotate(node *RedBlackTreeNode) {
	leftSubTree := node.left
	node.left = leftSubTree.right
	if leftSubTree.right != nil {
		leftSubTree.right.parent = node
	}
	leftSubTree.parent = node.parent
	if leftSubTree.parent == nil {
		t.root = leftSubTree
	} else if leftSubTree.parent.left == node {
		leftSubTree.parent.left = leftSubTree
	} else {
		leftSubTree.parent.right = leftSubTree
	}
	leftSubTree.right = node
	node.parent = leftSubTree
}

func (t *RedBlackTree) Add(value Comparable) {
	node := &RedBlackTreeNode{value: value, color: RED}
	if t.root == nil {
		t.root = node
		return
	}

	parent := t.root
	for iterator := t.root; iterator != nil; {
		parent = iterator
		if iterator.left.value.GreaterThan(value) {
			iterator = iterator.left
		} else {
			iterator = iterator.right
		}
	}

	node.parent = parent
	if parent.value.GreaterThan(value) {
		parent.left = node
	} else {
		parent.right = node
	}

	t.insertFixUp(node)
}

func (t *RedBlackTree) insertFixUp(node *RedBlackTreeNode) {

}

func (t *RedBlackTree) Remove(comparable Comparable) {

}
