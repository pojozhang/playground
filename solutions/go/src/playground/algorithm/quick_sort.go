package algorithm

func quickSort(array []int) {
	if len(array) <= 1 {
		return
	}

	left, right := 0, len(array)-1
	for left < right {
		for left < right && array[right] >= array[0] {
			right--
		}
		for left < right && array[left] <= array[0] {
			left++
		}
		array[left], array[right] = array[right], array[left]
	}

	array[0], array[left] = array[left], array[0]
	quickSort(array[:left])
	quickSort(array[left+1:])
}
