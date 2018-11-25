# [丑数](https://leetcode-cn.com/problems/ugly-number/description/)

编写一个程序判断给定的数是否为丑数。
丑数就是只包含质因数 `2, 3, 5` 的**正整数**。

**示例 1:**

```
输入: 6
输出: true
解释: 6 = 2 × 3
```

**示例 2:**

```
输入: 8
输出: true
解释: 8 = 2 × 2 × 2
```

**示例 3:**

```
输入: 14
输出: false 
解释: 14不是丑数，因为它包含了另外一个质因数7。
```

**说明：**
`1` 是丑数。
输入不会超过 32 位有符号整数的范围: [−2<sup>31</sup>,  2<sup>31</sup> − 1]。

## 实现

- [Go](https://github.com/pojozhang/playground/blob/master/solutions/go/src/playground/algorithm/ugly_number.go)
