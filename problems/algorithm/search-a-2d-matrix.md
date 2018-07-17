# [搜索二维矩阵](https://leetcode-cn.com/problems/search-a-2d-matrix/description/)

编写一个高效的算法来判断 m x n 矩阵中，是否存在一个目标值。该矩阵具有如下特性：
1. 每行中的整数从左到右按升序排列。
2. 每行的第一个整数大于前一行的最后一个整数。

**示例 1:**
```
输入:
matrix = [
  [1,   3,  5,  7],
  [10, 11, 16, 20],
  [23, 30, 34, 50]
]
target = 3
输出: true
```

**示例 2:**
```
输入:
matrix = [
  [1,   3,  5,  7],
  [10, 11, 16, 20],
  [23, 30, 34, 50]
]
target = 13
输出: false
```

#### 实现
- [Go](https://github.com/pojozhang/playground/blob/master/solutions/go/src/playground/algorithm/search_a_2d_matrix.go)
