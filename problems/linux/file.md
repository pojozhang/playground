# 文件

## 查看文件大小

我们可以通过`du`命令查看文件和文件夹的大小，`du`是Disk Usage的缩写。

```shell
# 查看指定目录的大小，结果只会显示指定目录的大小，不会递归地列出其子目录以及文件的大小。
du -sh path/to/directory

# 查看指定目录以及其所有子目录和文件的大小。
du -ah path/to/directory

# 查看指定目录以及其所有指定深度的子目录和文件的大小，这里的N是递归深度，当N等于0时，等价于du -sh path/to/directory。
du -h -d N path/to/directory
```
