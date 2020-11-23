# 限流

> 本文代码来源于 https://github.com/go-redis/redis_rate/blob/v9/lua.go。

```lua
redis.replicate_commands()
-- 限流指定的key。
local rate_limit_key = KEYS[1]
-- 漏桶的容量，也就是最多能容纳的令牌的数量。
local burst = ARGV[1]
-- 漏桶流出的速度，如每秒1个。
local rate = ARGV[2]
-- 单位时间，如每秒、每分、每小时。
local period = ARGV[3]
-- 本次消耗的令牌数。
local cost = tonumber(ARGV[4])
-- 产生一个令牌需要的单位时间。
local emission_interval = period / rate
-- 产生cost个令牌所需时长。
local increment = emission_interval * cost
-- 产生burst个令牌所需时长，也就是产生填满漏桶的令牌所需要的时长。
local burst_offset = emission_interval * burst

local jan_1_2017 = 1483228800
-- now记录了当前时间到2017-1-1 00:00:00的偏移量。
local now = redis.call("TIME")
now = (now[1] - jan_1_2017) + (now[2] / 1000000)
-- tat是Theoretical Arrival Time的缩写，表示理论到达时间。
local tat = redis.call("GET", rate_limit_key)
-- 如果tat不存在那么把tat设置为now。
if not tat then
  tat = now
else
  tat = tonumber(tat)
end
tat = math.max(tat, now)
-- 计算新的理论到达时间。
local new_tat = tat + increment
-- 计算理论允许获得令牌的时间，此处减去burst_offset相当于减去库存的令牌产生的时间。
local allow_at = new_tat - burst_offset
local diff = now - allow_at
-- 剩余令牌数 = （当前时间 - 理论允许获得令牌的时间）/ 产生一个令牌需要的单位时间。
local remaining = math.floor(diff / emission_interval + 0.5)
-- 剩余令牌数不足的情况。
if remaining < 0 then
  local reset_after = tat - now
  local retry_after = diff * -1
  return {
    0, -- allowed
    0, -- remaining
    tostring(retry_after),
    tostring(reset_after),
  }
end
-- 剩余令牌数充足的情况。
local reset_after = new_tat - now
redis.call("SET", rate_limit_key, new_tat, "EX", math.ceil(reset_after))
local retry_after = -1
return {cost, remaining, tostring(retry_after), tostring(reset_after)}
```
