package algorithm

import "math"

func largestRectangleArea(heights []int) int {
	if len(heights) == 0 {
		return 0
	}

	result := 0
	stack := NewIntStack()
	for i, height := range heights {
		for !stack.IsEmpty() && heights[stack.Top()] >= height {
			top := stack.Pop()
			if stack.IsEmpty() {
				// 栈为空，表示左边已经没有边界，此时宽度等于i
				//	  [top]
				//     *
				//     *    [i]
				// ... * ... *
				result = int(math.Max(float64(result), float64(heights[top]*i)))
			} else {
				// 栈不为空，表示左边还有边界，此时宽度等于i-1-stack.Top()
				//	   [top]
				//       *
				// *     *    [i]
				// * ... * ... *
				result = int(math.Max(float64(result), float64(heights[top]*(i-1-stack.Top()))))
			}
		}
		stack.Push(i)
	}

	// 如果栈不为空，那么栈中保存的是一个升序队列，所有的元素右边都没有界
	for !stack.IsEmpty() {
		top := stack.Pop()
		if stack.IsEmpty() {
			// 栈为空，表示左右都没有边界，此时宽度等于len(heights)
			//       [top]
			//         *
			//         *
			// ... ... * ... ...
			result = int(math.Max(float64(result), float64(heights[top]*(len(heights)))))
		} else {
			// 栈不为空，表示左边还有边界，右边没有界，此时宽度等于 len(heights)-stack.Top()-1
			//         [top]
			//           *
			//     *     *
			// ... * ... * ...
			result = int(math.Max(float64(result), float64(heights[top]*(len(heights)-stack.Top()-1))))
		}
	}

	return result
}
