package algorithm

func myPow(x float64, n int) float64 {
	if n >= 0 {
		return pow(x, n)
	}
	return 1 / pow(x, n)
}

func pow(x float64, n int) float64 {
	if n == 0 {
		return 1
	}
	if n == 1 {
		return x
	}

	v := pow(x, n/2)
	if n%2 == 0 {
		return v * v
	}
	return v * v * x
}
