package algorithm

import "math"

type MinStack struct {
	entries    []int
	minEntries []int
}

func Constructor() MinStack {
	return MinStack{entries: make([]int, 0), minEntries: make([]int, 0)}
}

func (this *MinStack) Push(x int) {
	this.entries = append(this.entries, x)
	if len(this.minEntries) == 0 {
		this.minEntries = append(this.minEntries, x)
		return
	}
	this.minEntries = append(this.minEntries, int(math.Min(float64(x), float64(this.minEntries[len(this.minEntries)-1]))))
}

func (this *MinStack) Pop() {
	this.entries = this.entries[0 : len(this.entries)-1]
	this.minEntries = this.minEntries[0 : len(this.minEntries)-1]
}

func (this *MinStack) Top() int {
	return this.entries[len(this.entries)-1]
}

func (this *MinStack) GetMin() int {
	return this.minEntries[len(this.minEntries)-1]
}
