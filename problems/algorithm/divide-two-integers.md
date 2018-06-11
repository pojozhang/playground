# [两数相除](https://leetcode-cn.com/problems/divide-two-integers/description/)

给定两个整数，被除数 `dividend` 和除数 `divisor`。将两数相除，要求不使用乘法、除法和 mod 运算符。
返回被除数 `dividend` 除以除数 `divisor` 得到的商。

**示例 1:**
```
输入: dividend = 10, divisor = 3
输出: 3
```

**示例 2:**
```
输入: dividend = 7, divisor = -3
输出: -2
```

**说明:**
1. 被除数和除数均为 32 位有符号整数。
2. 除数不为 0。
3. 假设我们的环境只能存储 32 位有符号整数，其数值范围是 [−2<sup>31</sup>,  2<sup>31</sup> − 1]。本题中，如果除法结果溢出，则返回 2<sup>31</sup> − 1。

#### 实现
- [Go](https://github.com/pojozhang/playground/blob/master/solutions/go/src/playground/algorithm/divide_two_integers.go)
