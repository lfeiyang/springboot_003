import com.sy.config.SpringConfig;
import com.sy.model.FrameUser;
import com.sy.util.RedisCatchUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

/**
 * Redis客户端Lettuce测试
 *
 * @author lfeiyang
 * @since 2022-05-02 1:41
 */
@SpringBootTest(classes = {SpringConfig.class})
@RunWith(value = SpringJUnit4ClassRunner.class)
public class JedisRedisTest {
    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @Autowired
    private RedisCatchUtil redisCatchUtil;

    @Test
    public void testSerializable() {
        FrameUser user = new FrameUser();
        user.setUserGuid(UUID.randomUUID().toString());
        user.setDisplayName("朝雾轻寒");
        user.setLoginId("15138");
        redisTemplate.opsForValue().set("user", user);

        FrameUser user2 = (FrameUser) redisTemplate.opsForValue().get("user");
        System.out.println(user);
    }

    @Test
    public void testKeys(){
        Set<String> keys = redisCatchUtil.keys("*m*");

        System.out.println(keys);
    }
}
