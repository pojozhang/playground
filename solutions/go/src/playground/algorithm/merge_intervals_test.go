package algorithm

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestMergeIntervalsCase1(t *testing.T) {
	actual := mergeIntervals([]Interval{{1, 3}, {2, 6}, {8, 10}, {15, 18}})

	assert.EqualValues(t, []Interval{{1, 6}, {8, 10}, {15, 18}}, actual)
}

func TestMergeIntervalsCase2(t *testing.T) {
	actual := mergeIntervals([]Interval{{1, 4}, {4, 5}})

	assert.EqualValues(t, []Interval{{1, 5}}, actual)
}

func TestMergeIntervalsCase3(t *testing.T) {
	actual := mergeIntervals([]Interval{{1, 4}, {0, 4}})

	assert.EqualValues(t, []Interval{{0, 4}}, actual)
}

func TestMergeIntervalsCase4(t *testing.T) {
	actual := mergeIntervals([]Interval{{1, 4}, {2, 3}})

	assert.EqualValues(t, []Interval{{1, 4}}, actual)
}
