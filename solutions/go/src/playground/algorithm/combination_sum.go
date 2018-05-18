package algorithm

import "sort"

func combinationSum(candidates []int, target int) [][]int {
	sort.Ints(candidates)

	length := 1
	for _, n := range candidates {
		if n != candidates[length-1] {
			candidates[length] = n
			length++
		}
	}

	result := make([][]int, 0)
	combinationSumR(candidates[0:length], 0, make([]int, 0), target, &result)
	return result
}

func combinationSumR(candidates []int, start int, combination []int, target int, result *[][]int) {
	if target < 0 {
		return
	}
	if target == 0 {
		tmp := make([]int, len(combination))
		copy(tmp, combination)
		*result = append(*result, tmp)
		return
	}

	for i := start; i < len(candidates); i++ {
		combinationSumR(candidates, i, append(combination, candidates[i]), target-candidates[i], result)
	}
}
