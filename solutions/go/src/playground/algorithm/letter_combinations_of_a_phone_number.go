package algorithm

var letterMapping = map[uint8][]string{
	'2': {"a", "b", "c"},
	'3': {"d", "e", "f"},
	'4': {"g", "h", "i"},
	'5': {"j", "k", "l"},
	'6': {"m", "n", "o"},
	'7': {"p", "q", "r", "s"},
	'8': {"t", "u", "v"},
	'9': {"w", "x", "y", "z"},
}

func letterCombinations(digits string) []string {
	if len(digits) == 0 {
		return []string{}
	}
	combinations := make([]string, 0)
	letterCombinationsR(digits, 0, "", &combinations)
	return combinations
}

func letterCombinationsR(digits string, start int, combination string, combinations *[]string) {
	if start == len(digits) {
		*combinations = append(*combinations, combination)
		return
	}
	digit := digits[start]
	letters := letterMapping[digit]
	for _, letter := range letters {
		letterCombinationsR(digits, start+1, combination+letter, combinations)
	}
}
