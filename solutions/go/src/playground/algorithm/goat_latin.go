package algorithm

import "strings"

func toGoatLatin(s string) string {
	strs := strings.Split(s, " ")
	words := make([]string, len(strs))
	for i, str := range strs {
		first := strings.ToLower(string(str[0]))
		if first == "a" || first == "e" || first == "i" || first == "o" || first == "u" {
			words[i] = str
		} else {
			words[i] = str[1:] + string(str[0])
		}
		words[i] += "ma"
		words[i] += strings.Repeat("a", i+1)
	}
	return strings.Join(words, " ")
}
