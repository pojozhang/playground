# 基础

## 命令

### 重命名

```bash
git mv <old_file> <new_file>
```

### 查看日志

```bash
git log [--oneline] [-n<N>] [--graph] [--all]
```

- oneline：仅显示commit message
- n：显示最近的N个提交
- graph：以图形的方式显示
- all：显示所有分支信息，如果不设置只显示当前的分支信息

### 切分支

```bash
git checkout <branch>
```
