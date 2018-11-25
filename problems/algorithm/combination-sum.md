# [组合总和](https://leetcode-cn.com/problems/combination-sum/description/)

给定一个**无重复元素**的数组 `candidates` 和一个目标数 `target` ，找出 `candidates` 中所有可以使数字和为 `target` 的组合。
`candidates` 中的数字可以无限制重复被选取。

**说明：**
所有数字（包括 `target`）都是正整数。
解集不能包含重复的组合。

**示例 1:**

```
输入: candidates = `[2,3,6,7], `target = `7`,
所求解集为:
[
  [7],
  [2,2,3]
]
```

**示例 2:**

```
输入: candidates = [2,3,5]`, `target = 8,
所求解集为:
[
  [2,2,2,2],
  [2,3,3],
  [3,5]
]
```

## 实现

- [Java](https://github.com/pojozhang/playground/blob/master/solutions/java/src/main/java/playground/algorithm/CombinationSum.java)
- [Go](https://github.com/pojozhang/playground/blob/master/solutions/go/src/playground/algorithm/combination_sum.go)
