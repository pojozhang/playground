package algorithm

import (
	"sort"
	"math"
)

type Interval struct {
	Start int
	End   int
}

type Intervals []Interval

func (i Intervals) Len() int {
	return len(i)
}

func (i Intervals) Swap(x, y int) {
	i[x], i[y] = i[y], i[x]
}

func (i Intervals) Less(x, y int) bool {
	if i[x].Start <= i[y].Start {
		return true
	}
	return false
}

func mergeIntervals(intervals []Interval) []Interval {
	if len(intervals) <= 1 {
		return intervals
	}

	sort.Sort(Intervals(intervals))
	result := make([]Interval, 0)
	result = append(result, intervals[0])
	for i := 1; i < len(intervals); i++ {
		if intervals[i].Start <= result[len(result)-1].End {
			result[len(result)-1].End = int(math.Max(float64(intervals[i].End), float64(result[len(result)-1].End)))
			continue
		}
		result = append(result, intervals[i])
	}
	return result
}
