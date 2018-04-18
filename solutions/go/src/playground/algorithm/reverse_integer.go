package algorithm

func reverseInteger(x int) int {
	result := int64(0)
	for x != 0 {
		result = result*10 + int64(x)%10
		x /= 10
	}

	if result > 2147483647 || result < -2147483648 {
		return 0
	}

	return int(result)
}
