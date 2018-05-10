package algorithm

import (
	"strings"
)

func longestPalindrome(s string) string {
	e := "#"
	for _, t := range s {
		e += string(t) + "#"
	}

	p, id, mx, pos := make([]int, len(e)), 0, 0, 0
	for i := range e {
		if i < mx {
			if mx-i < p[2*id-i] {
				p[i] = mx - i
			} else {
				p[i] = p[2*id-i]
			}
		} else {
			p[i] = 1
		}

		for i-p[i] >= 0 && i+p[i] < len(e) && e[i-p[i]] == e[i+p[i]] {
			p[i]++
		}

		if i+p[i] > mx {
			mx = i + p[i]
			id = i
		}

		if p[pos] < p[i] {
			pos = i
		}
	}

	len := p[pos] - 1
	return strings.Replace(e[pos-len:pos+len+1], "#", "", -1)
}
