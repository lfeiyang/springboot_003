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
