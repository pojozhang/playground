# Guava

## 限流

`RateLimiter`类实现了令牌算法，提供了限流的功能，通过`acquire()`方法可以获取一个令牌，如果令牌已被领完，该方法会一直阻塞。

以下代码以每秒1个的速率发放令牌，执行效果时每隔1秒打印`hello`。

```java
public static void main(String[] args) throws InterruptedException {
    RateLimiter rateLimiter = RateLimiter.create(1);
    for (int i = 0; i < 10; i++) {
        rateLimiter.acquire();
        System.out.println("hello");
    }
}
```
