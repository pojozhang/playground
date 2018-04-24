package algorithm

func longestCommonPrefix(strs []string) string {
	if len(strs) < 1 {
		return ""
	}

	i := -1
	for ; i+1 < len(strs[0]); i++ {
		key := strs[0][i+1]

		for j := 1; j < len(strs); j++ {
			if !(i+1 < len(strs[j]) && strs[j][i+1] == key) {
				goto T
			}
		}
	}

T:
	if i < 0 {
		return ""
	}
	return strs[0][:i+1]
}
