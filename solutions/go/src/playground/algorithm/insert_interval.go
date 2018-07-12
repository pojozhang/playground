package algorithm

import "math"

func insert(intervals []Interval, newInterval Interval) []Interval {
	if len(intervals) == 0 {
		return []Interval{newInterval}
	}

	result := make([]Interval, 0)
	start, end := newInterval.Start, newInterval.End
	for _, v := range intervals {
		if start > v.End { // 区域不重叠且区域v在左侧
			result = append(result, Interval{Start: v.Start, End: v.End})
		} else if end < v.Start { // 区域不重叠且区域v在右侧
			result = append(result, Interval{Start: start, End: end})
			start = v.Start
			end = v.End
		} else { //	区域有重叠
			start = int(math.Min(float64(start), float64(v.Start)))
			end = int(math.Max(float64(end), float64(v.End)))
		}
	}
	result = append(result, Interval{Start: start, End: end})
	return result
}
