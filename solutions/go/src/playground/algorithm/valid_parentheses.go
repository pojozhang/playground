package algorithm

func isValid(s string) bool {
	mapping := map[rune]rune{')': '(', ']': '[', '}': '{'}

	var stack []rune
	for _, c := range s {
		if c == '(' || c == '[' || c == '{' {
			stack = append(stack, c)
			continue
		}
		if len(stack) > 0 && stack[len(stack)-1] == mapping[c] {
			stack = stack[0 : len(stack)-1]
		} else {
			return false
		}
	}

	return len(stack) == 0
}
