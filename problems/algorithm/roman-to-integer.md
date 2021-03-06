# [罗马数字转整数](https://leetcode-cn.com/problems/roman-to-integer/description/)

罗马数字包含以下七种字符: `I`， `V`， `X`， `L`，`C`，`D` 和 `M`。

```
字符          数值
I             1
V             5
X             10
L             50
C             100
D             500
M             1000
```

例如， 罗马数字 2 写做 `II` ，即为两个并排放置的的 1。12 写做 `XII` ，即为 `X` + `II` 。 27 写做  `XXVII`, 即为 `XX` + `V` + `II` 。

在罗马数字中，小的数字在大的数字的右边。但 4 不写作 `IIII`，而是 `IV`。数字 1 在数字 5 的左边，所表示的数等于大数减小数得到的数值 4 。同样地，数字 9 表示为 `IX`。这个规则只适用于以下六种情况：

`I` 可以放在 `V` (5) 和 `X` (10) 的左边，来表示 4 和 9。
`X` 可以放在 `L` (50) 和 `C` (100) 的左边，来表示 40 和 90。 
`C` 可以放在 `D` (500) 和 `M` (1000) 的左边，来表示 400 和 900。
给定一个罗马数字，将其转换成整数。输入确保在 1 到 3999 范围内。

示例 1:

```
输入: "III"
输出: 3
```

示例 2:

```
输入: "IV"
输出: 4
```

示例 3:

```
输入: "IX"
输出: 9
```

示例 4:

```
输入: "LVIII"
输出: 58
解释: C = 100, L = 50, XXX = 30 and III = 3.
```

示例 5:

```
输入: "MCMXCIV"
输出: 1994
解释: M = 1000, CM = 900, XC = 90 and IV = 4.
```

## 实现

- [Java](https://github.com/pojozhang/playground/blob/master/solutions/java/src/main/java/playground/algorithm/RomanToInteger.java)
- [Go](https://github.com/pojozhang/playground/blob/master/solutions/go/src/playground/algorithm/roman_to_integer.go)
