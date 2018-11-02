package tree

import . "playground/structure"

type BinaryTreeNode struct {
	key    Comparable
	parent *BinaryTreeNode
	left   *BinaryTreeNode
	right  *BinaryTreeNode
}
