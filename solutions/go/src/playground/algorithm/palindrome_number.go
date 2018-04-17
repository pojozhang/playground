package algorithm

func isPalindrome(x int) bool {
	if x < 0 {
		return false
	}

	d := 1
	for x/d >= 10 {
		d *= 10
	}

	a, b := x, x
	for b > 0 {
		if a/d != b%10 {
			return false
		}
		a %= d
		d /= 10
		b /= 10
	}

	return true
}
