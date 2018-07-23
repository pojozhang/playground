package algorithm

type ListNode struct {
	Val  int
	Next *ListNode
}

type TreeNode struct {
	Val   int
	Left  *TreeNode
	Right *TreeNode
}

type Interval struct {
	Start int
	End   int
}

type IntStack []int

func NewIntStack() *IntStack {
	return &IntStack{}
}

func (s *IntStack) Push(v int) {
	*s = append(*s, v)
}

func (s *IntStack) Top() int {
	return (*s)[len(*s)-1]
}

func (s *IntStack) Pop() int {
	top := s.Top()
	*s = (*s)[:len(*s)-1]
	return top
}

func (s *IntStack) IsEmpty() bool {
	return len(*s) == 0
}
