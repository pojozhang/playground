# [Z字形变换](https://leetcode-cn.com/problems/zigzag-conversion/description/)

将字符串 `"PAYPALISHIRING"` 以Z字形排列成给定的行数：
```
P   A   H   N
A P L S I I G
Y   I   R
```
之后从左往右，逐行读取字符：`"PAHNAPLSIIGYIR"`

实现一个将字符串进行指定行数变换的函数:
```
string convert(string text, int nRows);
```

示例 1:
```
输入: s = "PAYPALISHIRING", numRows = 3
输出: "PAHNAPLSIIGYIR"
```

示例 2:
```
输入: s = "PAYPALISHIRING", numRows = 4
输出: "PINALSIGYAHRPI"
解释:

P     I    N
A   L S  I G
Y A   H R
P     I
```

#### 实现
