package tree

import . "playground/structure"

type BinaryTreeNode struct {
	value  Comparable
	parent *BinaryTreeNode
	left   *BinaryTreeNode
	right  *BinaryTreeNode
}
