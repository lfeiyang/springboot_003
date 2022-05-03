# <font face=幼圆 color=white>[Redis](https://redis.io/)</font>

## <font face=幼圆 color=white>一、分布式锁</font>

### <font face=幼圆 color=white>1.1.引言</font>

```text
	我们在系统中修改已有数据时，需要先读取，然后进行修改保存，此时很容易遇到并发问
题。由于修改和保存不是原子操作，在并发场景下，部分对数据的操作可能会丢失。在单服务器
系统我们常用本地锁来避免并发带来的问题，然而，当服务采用集群方式部署时，本地锁无法在
多个服务器之间生效，这时候保证数据的一致性就需要分布式锁来实现。
```



![img](https://xiaomi-info.github.io/2019/12/17/redis-distributed-lock/redis-lock-01.png)

### <font face=幼圆 color=white>1.2.实现</font>

<font face=幼圆 color=white>Redis 锁主要利用 Redis 的 setnx 命令</font>

```text
1.加锁命令：SETNX key value，当键不存在时，对键进行设置操作并返回成功，否则返回失败。
KEY 是锁的唯一标识，一般按业务来决定命名。

2.解锁命令：DEL key，通过删除键值对释放锁，以便其他线程可以通过 SETNX 命令来获取锁。


3.锁超时：EXPIRE key timeout, 设置 key 的超时时间，以保证即使锁没有被显式释放，
锁也可以在一定时间后自动释放，避免资源被永远锁住
```



#### <font face=幼圆 color=white>1.2.1.SETNX 和 EXPIRE 非原子性</font>

```lua
if redis.call('setnx', KEYS[1], ARGV[1]) then
    return redis.call('expire', KEYS[1], ARGV[2])
else
    return -1000
end
```



#### <font face=幼圆 color=white>1.2.2.锁误解除</font>

```lua
if redis.call("get", KEYS[1]) == ARGV[1] then
    return redis.call("del", KEYS[1])
else
    return 0
end
```



#### <font face=幼圆 color=white>1.2.3.超时解锁导致并发</font>

```text
将过期时间设置足够长，确保代码逻辑在锁释放之前能够执行完成
```



### <font face=幼圆 color=white>1.3.测试</font>

```java
import com.sy.config.SpringConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.UUID;

/**
 * Lua脚本测试
 *
 * @author lfeiyang
 * @since 2022-05-03 17:52
 */
@SpringBootTest(classes = {SpringConfig.class})
@RunWith(value = SpringJUnit4ClassRunner.class)
public class LuaTest {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final String lockKey = "LuaKey";

    @Test
    public void listPopN() {
        // 标识
        String uuid = UUID.randomUUID().toString().replace("-", "");

        // 执行 lua 脚本
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();

        // 1)指定 lua 脚本
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("redis/listPopN.lua")));

        // 2)指定返回类型
        redisScript.setResultType(Long.class);

        // 3)参数一：redisScript，参数二：key列表，参数三：arg（可多个）
        Long result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), uuid, 100);

        System.out.println(result);
    }

    @Test
    public void delKey() {
        // 执行 lua 脚本
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();

        // 1)指定 lua 脚本
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("redis/DelKey.lua")));

        // 2)指定返回类型
        redisScript.setResultType(Long.class);

        // 3)参数一：redisScript，参数二：key列表，参数三：arg（可多个）
        Long result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), "2e4b750e4efe4b3f875b909f58fc269f");

        System.out.println(result);
    }
}
```

