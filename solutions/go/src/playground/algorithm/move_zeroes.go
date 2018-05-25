package algorithm

func moveZeroes(nums []int)  {
	pos:=0

	for i:=0;i<len(nums);i++{
		if nums[i]!=0{
			nums[pos] = nums[i]
			pos++
		}
	}

	for ;pos<len(nums);pos++{
		nums[pos]=0
	}
}