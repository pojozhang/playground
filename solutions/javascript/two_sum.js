const NUMS = [2, 7, 11, 15];
const TARGET = 13;


const twoSum = (nums, target) => {
  for (let i = 0;i < nums.length; i++) {
    for (let j = 0; j< nums.length; j++) {
      if (i !== j && nums[i] + nums[j] === target) {
        return [i, j];
      }
    }
  }
  return 'no solution';
};


console.log(twoSum(NUMS, TARGET));
