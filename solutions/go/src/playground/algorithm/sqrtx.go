package algorithm

func mySqrt(x int) int {
	l, r := 0, x

	for l < r {
		m := l + (r-l)/2
		v := m * m

		if v == x {
			return m
		}

		if v < x {
			l = m + 1
		} else {
			r = m - 1
		}
	}

	if l*l > x {
		l--
	}

	return l
}
