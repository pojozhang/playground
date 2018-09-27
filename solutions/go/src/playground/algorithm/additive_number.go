package algorithm

import (
	"strconv"
	"strings"
)

func isAdditiveNumber(num string) bool {
	for middle := 1; middle < len(num)-1; middle++ {
		for end := middle + 1; end < len(num); end++ {
			if isAdditiveNumberR(num, 0, middle, end) {
				return true
			}
		}
	}
	return false
}

func isAdditiveNumberR(num string, start, middle, end int) bool {
	// 如果第二个数字的字符串表示中第一位是0并且字符串长度大于1，那么就不满足。
	// 比如"0235813"，"02"+"3" = "5"实际上是不成立的，因为"0"和"2"是独立的。
	if num[start] == '0' && start+1 < middle {
		return false
	}
	// 如果第二个数字的字符串表示中第一位是0并且字符串长度大于1，那么就不满足。
	// 比如"1023"，"1"+"02" = "3"实际上是不成立的，因为"0"和"2"是独立的。
	if num[middle] == '0' && middle+1 < end {
		return false
	}
	first, _ := strconv.ParseInt(num[start:middle], 10, 64)
	second, _ := strconv.ParseInt(num[middle:end], 10, 64)
	sum := strconv.FormatInt(first+second, 10)
	if strings.Index(num[end:], sum) == 0 {
		if end+len(sum) == len(num) {
			return true
		}
		return isAdditiveNumberR(num, middle, end, end+len(sum))
	}
	return false
}
