package tree

type RedBlackTreeNode struct {
	key    interface{}
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

func (t *RedBlackTree) leftRotate(node *RedBlackTreeNode) {
	rightSubTree := node.right
	node.right = rightSubTree.left
	if rightSubTree.left != nil {
		rightSubTree.left.parent = node
	}

	rightSubTree.left = node
}

func (t *RedBlackTree) rightRotate(node *RedBlackTreeNode) {
	parent := node.parent
	parent.left = node.right
	node.right = parent
	node.parent = parent.parent
	parent.parent = node
	if parent.parent.left == parent {
		parent.parent.left = node
	} else {
		parent.parent.right = node
	}
}
