package main

import (
	"fmt"
)

func twoSum(nums []int, target int) []int {
	for i := 0 ; i < len(nums) ; i ++ {
		for j := i+ 1; j < len(nums) ; j ++ {
			if nums[i] + nums[j] == target {
				return []int{i , j}
			}
		}
	}
    return nums
}


func main() {

	nums := []int{1,5,5,20}
	target := 10

	expect := []int{1,2}
	answer := twoSum(nums , target)
	result := true

	for index, value := range expect {	
		if answer[index] != value {
			result = false
		}
	}

	if result {
		fmt.Println("Success")
	} else {
		fmt.Println("Oh no")
	}
}