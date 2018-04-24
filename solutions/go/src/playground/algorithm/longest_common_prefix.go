package algorithm

func longestCommonPrefix(strs []string) string {
	if len(strs) < 1 {
		return ""
	}

	key, l := strs[0], len(strs[0])
	for _, s := range strs[1:] {
		if len(s) < l {
			l = len(s)
		}
		for j := 0; j < l; j++ {
			if key[j] != s[j] {
				l = j
				break
			}
		}
	}

	return key[:l]
}
