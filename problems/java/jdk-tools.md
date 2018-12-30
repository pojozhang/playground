# JDK工具

## jps

JVM Process Status Tool可以列出正在运行的虚拟机进程。

对下面的代码进行编译后使用`java Test a b`运行编译后的类文件，这里的`a`、`b`是传递给`main()`方法的参数。

```java
public class Test {

    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(1000000);
    }
}
```

在另一个进程中执行`jps -ml`可以看到类似以下的结果，分别是进程号、`main()`方法所在的类的名称以及传递给`main()`方法的参数。

```bash
30273 Test a b
```

## jstat

JVM Statistics Monitoring Tool用于监视虚拟机各种运行状态的信息，主要包括类装载、垃圾回收、运行时编译方面的相关信息。

用命令`jstat -gc 31030`查看31030进程的垃圾回收状况，我们会得到如下内容。

```bash
S0C    S1C    S0U    S1U      EC       EU        OC         OU       MC     MU    CCSC   CCSU   YGC     YGCT    FGC    FGCT    CGC    CGCT     GCT   
 0.0    0.0    0.0    0.0   26624.0   1024.0   235520.0     0.0      0.0    0.0    0.0    0.0        0    0.000   0      0.000   0      0.000    0.000
```

- S0C：Survivor0的容量，单位KB。
- S1C：Survivor1的容量，单位KB。
- S0U：Survivor0的已用容量，单位KB。
- S1U：Survivor1的已用容量，单位KB。
- EC：Eden区的容量，单位KB。
- EU：Eden区的已用容量，单位KB。
- OC：老年代的容量，单位KB。
- OU：老年代的已用容量，单位KB。
- MC：元空间的容量，单位KB。
- MU：元空间的已用容量，单位KB。
- CCSC：压缩类空间的容量，单位KB。
- CCSU：压缩类空间的已用容量，单位KB。
- YGC：Young GC的次数。
- YGCT：Young GC总耗时。
- FGC：Full GC的次数。
- FGCT：Full GC总耗时。
- CGC：Stop-The-World的次数。
- CGCT：Stop-The-World的总耗时。
- GCT：GC总耗时。

## jmap

## jhat

## jstack
