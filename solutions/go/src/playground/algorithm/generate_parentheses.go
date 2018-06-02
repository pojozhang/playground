package algorithm

func generateParenthesis(n int) []string {
	result := make([]string, 0)
	generateParenthesisR(1, 0, n, "(", &result)
	return result
}

func generateParenthesisR(left, right, n int, combination string, result *[]string) {
	if left < right || left > n {
		return
	}

	if right == n {
		*result = append(*result, combination)
	}

	generateParenthesisR(left+1, right, n, combination+"(", result)
	generateParenthesisR(left, right+1, n, combination+")", result)
}
