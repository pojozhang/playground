package algorithm

func romanToInt(s string) int {
	symbols := map[rune]int{'I': 1, 'V': 5, 'X': 10, 'L': 50, 'C': 100, 'D': 500, 'M': 1000}

	result := 0
	for i, previous := len(s)-1, 0; i >= 0; i-- {
		v := symbols[rune(s[i])]
		if v >= previous {
			result += v
		} else {
			result -= v
		}
		previous = v
	}
	return result
}
