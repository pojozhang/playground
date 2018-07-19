package algorithm

import "strconv"

func evalRPN(tokens []string) int {
	stack := make([]int, 0)
	operations := map[string]func(int, int) int{
		"+": addFunc,
		"-": minusFunc,
		"*": multiplyFunc,
		"/": divideFunc,
	}

	for _, token := range tokens {
		if opt, ok := operations[token]; ok {
			v := opt(stack[len(stack)-2], stack[len(stack)-1])
			stack = stack[:len(stack)-1]
			stack[len(stack)-1] = v
			continue
		}

		if n, err := strconv.ParseInt(token, 10, 32); err == nil {
			stack = append(stack, int(n))
			continue
		}
	}
	return stack[0]
}

func addFunc(a, b int) int {
	return a + b
}

func minusFunc(a, b int) int {
	return a - b
}

func multiplyFunc(a, b int) int {
	return a * b
}

func divideFunc(a, b int) int {
	return a / b
}
