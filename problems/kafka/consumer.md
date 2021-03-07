# 消费者

## 自动提交


```java
public void maybeAutoCommitOffsetsAsync(long now) {
    if (autoCommitEnabled) {
        nextAutoCommitTimer.update(now);
        if (nextAutoCommitTimer.isExpired()) {
            nextAutoCommitTimer.reset(autoCommitIntervalMs);
            doAutoCommitOffsetsAsync();
        }
    }
}
```