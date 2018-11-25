# 最大公约数

求两个数的最大公约数。

## 实现

- [Java](https://github.com/pojozhang/playground/blob/master/solutions/java/src/main/java/playground/algorithm/GreatestCommonDivisor.java)

## 思路

1. 欧几里得算法（辗转相除法）

**计算公式**

```
gcd(a,b) = gcd(b,a mod b)
```

**证明**

1. 假设 m = gcd(a,b)，m是a和b的最大公约数，则m可以被a和b整除，记作m|a，m|b。
2. 假设 n = gcd(b,a mod b)，n是b和a mod b的最大公约数。同理，n|b，n|a mod b。
3. 因为 a = x*b + a mod b，所以 a/n = x*(b/n) + (a mod b)/n，由2可知 n|b，n|a mod b，所以 n|a，所以n是a和b的公约数。
4. 因为 m是a和b的最大公约数，所以m>=n。
5. 另一方面 a mod b = a - y*b，所以(a mod b)/m = a/m - y*(b/m)，由1可知 m|a，m|b，所以 m|a mod b，所以n是b和a mod b的公约数。
6. 因为n是b和a mod b的最大公约数，所以n>=m。
7. 由4和6可知，m = n，即 gcd(a,b) = gcd(b,a mod b)。

![动画演示](resources/greatest-common-divisor-1.gif)

图片来源: https://xuanwo.org/2015/03/11/number-theory-gcd/
