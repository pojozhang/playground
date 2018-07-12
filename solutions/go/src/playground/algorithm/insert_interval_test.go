package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestInsertCase1(t *testing.T) {
	actual := insert([]Interval{{Start: 1, End: 3}, {Start: 6, End: 9}}, Interval{Start: 2, End: 5})

	assert.Equal(t, []Interval{{Start: 1, End: 5}, {Start: 6, End: 9}}, actual)
}

func TestInsertCase2(t *testing.T) {
	actual := insert(
		[]Interval{
			{Start: 1, End: 2},
			{Start: 3, End: 5},
			{Start: 6, End: 7},
			{Start: 8, End: 10},
			{Start: 12, End: 16},
		}, Interval{Start: 4, End: 8})

	assert.Equal(t, []Interval{{Start: 1, End: 2}, {Start: 3, End: 10}, {Start: 12, End: 16}}, actual)
}
