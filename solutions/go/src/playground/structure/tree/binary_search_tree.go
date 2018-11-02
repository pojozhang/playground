package tree

import . "playground/structure"

type BinarySearchTree struct {
	root *BinaryTreeNode
}

func NewBinarySearchTree() *BinarySearchTree {
	t := &BinarySearchTree{}
	return t
}

func (t *BinarySearchTree) Add(value Comparable) {
	if t.root == nil {
		t.root = &BinaryTreeNode{parent: nil, key: value}
		return
	}

	var parent *BinaryTreeNode
	for node := t.root; node != nil; {
		parent = node
		if node.key.GreaterThan(value) {
			node = node.left
		} else if node.key.LessThan(value) {
			node = node.right
		} else {
			return
		}
	}

	if parent.key.GreaterThan(value) {
		parent.left = &BinaryTreeNode{parent: parent, key: value}
		return
	}
	parent.right = &BinaryTreeNode{parent: parent, key: value}
}

func (t *BinarySearchTree) Remove(value Comparable) {
	if node, ok := t.find(t.root, value); ok {
		/*
				1(node)					3(node)
			   / \					   /
			 nil  3			==>		  2
		         /
				2
		 */
		if node.left == nil {
			t.transplant(node, node.right)
			return
		}
		/*
				3(node)					 2(node)
			   / \						/
		      2  nil		==>		   1
		     /
		    1
		 */
		if node.right == nil {
			t.transplant(node, node.left)
			return
		}
		/*
				6(node)					 10(node)
			   / \						/  \
		      2   10		==>		   2    12
		         /  \
		        nil  12
		 */
		if node.right.left == nil {
			node.right.left = node.left
			t.transplant(node, node.right)
			return
		}
		/*
				20(node)				      25(node)
		       /  \						     /  \
		     10    40		 ==>		   10    40
		          /  \                          /  \
		         30   50                       30  50
		        /                             /
		       25(successor)                 27
		         \
		          27
		*/
		successor, _ := t.successor(node)
		t.transplant(successor, successor.right)
		successor.left, successor.right = node.left, node.right
		t.transplant(node, successor)
	}
}

func (t *BinarySearchTree) find(node *BinaryTreeNode, value Comparable) (*BinaryTreeNode, bool) {
	if node == nil {
		return nil, false
	}

	if node.key.Equals(value) {
		return node, true
	}

	if node.key.GreaterThan(value) {
		return t.find(node.left, value)
	}

	return t.find(node.right, value)
}

func (t *BinarySearchTree) successor(node *BinaryTreeNode) (*BinaryTreeNode, bool) {
	if node.right != nil {
		return t.min(node.right), true
	}
	return nil, false
}

func (t *BinarySearchTree) predecessor(node *BinaryTreeNode) (*BinaryTreeNode, bool) {
	if node.left != nil {
		return t.max(node.left), true
	}
	return nil, false
}

func (t *BinarySearchTree) max(node *BinaryTreeNode) *BinaryTreeNode {
	for node.right != nil {
		node = node.right
	}
	return node
}

func (t *BinarySearchTree) min(node *BinaryTreeNode) *BinaryTreeNode {
	for node.left != nil {
		node = node.left
	}
	return node
}

func (t *BinarySearchTree) transplant(oldRoot, newRoot *BinaryTreeNode) {
	newRoot.parent = oldRoot.parent
	if oldRoot.parent == nil {
		t.root = newRoot
		newRoot.parent = nil
		return
	}
	if oldRoot.parent.left == oldRoot {
		oldRoot.parent.left = newRoot
		return
	}
	oldRoot.parent.right = newRoot
}
