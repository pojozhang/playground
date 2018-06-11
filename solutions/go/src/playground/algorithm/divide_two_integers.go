package algorithm

import "math"

func divide(dividend int, divisor int) int {
	a, b := int64(math.Abs(float64(dividend))), int64(math.Abs(float64(divisor)))
	if a < b {
		return 0
	}

	tempDivisor := b // 复制一份除数的绝对值用于后续处理
	result := int64(0)
	count := int64(1)

	for a > 0 {
		for a >= b {
			b <<= 1
			count <<= 1
		}
		result += count >> 1
		a -= b >> 1
		// 除数还原为一开始传进来的除数的绝对值,count也还原成1
		b, count = tempDivisor, 1
	}

	// 判断正负
	negative := false
	if dividend > 0 && divisor < 0 || dividend < 0 && divisor > 0 {
		negative = true
	}

	// 如果溢出则返回2^31 - 1
	if !negative && result > math.MaxInt32 || result < math.MinInt32 {
		return math.MaxInt32
	}

	if negative {
		result = 0 - result
	}
	return int(result)
}
