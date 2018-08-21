package algorithm

func reverseString(s string) string {
	var str string
	for _, c := range s {
		str = string(c) + str
	}
	return str
}
