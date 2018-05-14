# [直线上最多的点数](https://leetcode-cn.com/problems/max-points-on-a-line/description/)

给定一个二维平面，平面上有 n 个点，求最多有多少个点在同一条直线上。

**示例 1:**
```
输入: [[1,1],[2,2],[3,3]]
输出: 3
解释:
^
|
|        o
|     o
|  o  
+------------->
0  1  2  3  4
```

**示例 2:**
```
输入: [[1,1],[3,2],[5,3],[4,1],[2,3],[1,4]]
输出: 4
解释:
^
|
|  o
|     o        o
|        o
|  o        o
+------------------->
0  1  2  3  4  5  6
```

#### 实现
- [Java](https://github.com/pojozhang/playground/blob/master/solutions/java/src/main/java/playground/algorithm/MaxPointsOnALine.java)
