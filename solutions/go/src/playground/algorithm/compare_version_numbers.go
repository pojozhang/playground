package algorithm

import (
	"strings"
	"strconv"
)

func compareVersion(version1 string, version2 string) int {
	s1, s2 := strings.Split(version1, "."), strings.Split(version2, ".")

	for i := 0; i < len(s1) && i < len(s2); i++ {
		n1, _ := strconv.ParseInt(s1[i], 10, 32)
		n2, _ := strconv.ParseInt(s2[i], 10, 32)
		if n1 > n2 {
			return 1
		}
		if n1 < n2 {
			return -1
		}
	}

	if len(s1) > len(s2) {
		for i := len(s2); i < len(s1); i++ {
			n, _ := strconv.ParseInt(s1[i], 10, 32)
			if n > 0 {
				return 1
			}
		}
	} else if len(s1) < len(s2) {
		for i := len(s1); i < len(s2); i++ {
			n, _ := strconv.ParseInt(s2[i], 10, 32)
			if n > 0 {
				return -1
			}
		}
	}
	return 0
}
