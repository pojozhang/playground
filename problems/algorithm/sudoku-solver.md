# [解数独](https://leetcode-cn.com/problems/sudoku-solver/description/)

编写一个程序，通过已填充的空格来解决数独问题。

一个数独的解法需**遵循如下规则**：

1. 数字 `1-9` 在每一行只能出现一次。
2. 数字 `1-9` 在每一列只能出现一次。
3. 数字 `1-9` 在每一个以粗实线分隔的 `3x3` 宫内只能出现一次。

空白格用 `'.'` 表示。

![](http://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Sudoku-by-L2G-20050714.svg/250px-Sudoku-by-L2G-20050714.svg.png)

一个数独。

![](http://upload.wikimedia.org/wikipedia/commons/thumb/3/31/Sudoku-by-L2G-20050714_solution.svg/250px-Sudoku-by-L2G-20050714_solution.svg.png)

答案被标成红色。

**注意:**

1. 给定的数独序列只包含数字 `1-9` 和字符 `'.'` 。
2. 你可以假设给定的数独只有唯一解。
3. 给定数独永远是 `9x9` 形式的。

## 实现

- [Go](https://github.com/pojozhang/playground/blob/master/solutions/go/src/playground/algorithm/sudoku_solver.go)
