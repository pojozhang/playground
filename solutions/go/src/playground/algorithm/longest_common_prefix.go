package algorithm

func longestCommonPrefix(strs []string) string {
	if len(strs) < 1 {
		return ""
	}

	i := -1
	for {
		if i+1 >= len(strs[0]) {
			break
		}

		key := strs[0][i+1]

		for j := 1; j < len(strs); j++ {
			if !(i+1 < len(strs[j]) && strs[j][i+1] == key) {
				goto T
			}
		}

		i++
	}

T:
	if i < 0 {
		return ""
	}
	return strs[0][:i+1]
}
