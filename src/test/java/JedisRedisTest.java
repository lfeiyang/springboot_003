import com.sy.config.SpringConfig;
import com.sy.model.FrameUser;
import com.sy.util.RedisCatchUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tk.mybatis.mapper.util.Assert;

import java.util.*;

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
    private RedisTemplate<String, Object> redisTemplate;

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
        System.out.println(user2);
    }

    @Test
    public void keys() {
        Set<String> keys = redisCatchUtil.keys("*m*");

        System.out.println(keys);
    }

    @Test
    public void expire() {
        assert redisCatchUtil.expire("Jedis", 30);
    }

    @Test
    public void getExpire() {
        long expire = redisCatchUtil.getExpire("Jedis");

        System.out.println(expire);
    }

    @Test
    public void hasKey() {
        Assert.isTrue(redisCatchUtil.hasKey("Jedis"), "redis中Jedis键不存在！");
    }

    @Test
    public void del() {
        redisCatchUtil.del("Jedis");
    }

    @Test
    public void get() {
        Object jedis = redisCatchUtil.get("Jedis");

        System.out.println(jedis);
    }

    @Test
    public void set() {
        assert redisCatchUtil.set("Jedis", "Hello Jedis！");
    }

    @Test
    public void setNx() {
        assert redisCatchUtil.setNx("Jedis", "Hello Jedis！");
    }

    @Test
    public void setWithTime() {
        assert redisCatchUtil.set("tempJedis", "Hello Jedis！", 60);
    }

    @Test
    public void setNxWithTime() {
        assert redisCatchUtil.setNx("tempJedis", "Hello Jedis！", 60);
    }

    @Test
    public void inc() {
        long incr = redisCatchUtil.incr("number", 2);

        System.out.println(incr);
    }

    @Test
    public void decr() {
        long number = redisCatchUtil.decr("number", 2);

        System.out.println(number);
    }

    @Test
    public void hGet() {
        Object jedisSet = redisCatchUtil.hGet("JedisHash", "set1");

        System.out.println(jedisSet);
    }

    @Test
    public void hmGet() {
        Map<Object, Object> jedisSet = redisCatchUtil.hmGet("JedisHash");

        System.out.println(jedisSet);
    }

    @Test
    public void hmSet() {
        Map<String, Object> map = new HashMap<>();
        map.put("set1", 1.0);
        map.put("set2", 2.0);
        map.put("set3", 3.0);

        assert redisCatchUtil.hmSet("JedisHash", map);
    }

    @Test
    public void hmSetWithTime() {
        Map<String, Object> map = new HashMap<>();
        map.put("set1", "1");
        map.put("set2", "2");
        map.put("set3", "3");

        assert redisCatchUtil.hmSet("JedisHash", map, 30);
    }

    @Test
    public void hSet() {
        assert redisCatchUtil.hSet("JedisHash", "set4", 4.0);
    }

    @Test
    public void hSetWithTime() {
        assert redisCatchUtil.hSet("JedisHash", "set3", "3", 30);
    }

    @Test
    public void hDel() {
        redisCatchUtil.hDel("JedisHash", "set1");
    }

    @Test
    public void hHasKey() {
        Assert.isTrue(redisCatchUtil.hHasKey("JedisHash", "set3"), "不存在");
    }

    @Test
    public void hIncr() {
        double hIncr = redisCatchUtil.hIncr("JedisHash", "set4", 2.0);

        System.out.println(hIncr);
    }

    @Test
    public void hDecr() {
        double hDecr = redisCatchUtil.hDecr("JedisHash", "set4", 1.2);

        System.out.println(hDecr);
    }

    @Test
    public void sGet() {
        Set<Object> jedisSet = redisCatchUtil.sGet("JedisSet");

        System.out.println(jedisSet);
    }

    @Test
    public void sHasKey() {
        assert redisCatchUtil.sHasKey("JedisSet", 1);
    }

    @Test
    public void sSet() {
        long jedisSet = redisCatchUtil.sSet("JedisSet", 1, 2, 3);

        System.out.println(jedisSet);
    }

    @Test
    public void sSetAndTime() {
        long jedisSet = redisCatchUtil.sSetAndTime("JedisSet2", 60, 1, 2, 3);

        System.out.println(jedisSet);
    }

    @Test
    public void sGetSetSize() {
        long jedisSet = redisCatchUtil.sGetSetSize("JedisSet");

        System.out.println(jedisSet);
    }

    @Test
    public void setRemove() {
        long jedisSet = redisCatchUtil.setRemove("JedisSet", 3);

        System.out.println(jedisSet);
    }

    @Test
    public void lGet() {
        List<Object> jedisList = redisCatchUtil.lGet("JedisList", 0, -1);

        System.out.println(jedisList);
    }

    @Test
    public void lGetListSize() {
        long jedisList = redisCatchUtil.lGetListSize("JedisList");

        System.out.println(jedisList);
    }

    @Test
    public void lGetIndex() {
        Object jedisList = redisCatchUtil.lGetIndex("JedisList", 0);

        System.out.println(jedisList);
    }

    @Test
    public void lSet() {
        assert redisCatchUtil.lSet("JedisList", 1);
    }

    @Test
    public void lSetWithTime() {
        assert redisCatchUtil.lSet("JedisList", 1, 60);
    }

    @Test
    public void lSetList() {
        Object jedisList = redisCatchUtil.lSet("JedisList", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

        System.out.println(jedisList);
    }

    @Test
    public void lSetListWithTime() {
        Object jedisList = redisCatchUtil.lSet("JedisList", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), 30);

        System.out.println(jedisList);
    }

    @Test
    public void lUpdateIndex() {
        assert redisCatchUtil.lUpdateIndex("JedisList", 0, 60);
    }

    @Test
    public void lRemove() {
        long jedisList = redisCatchUtil.lRemove("JedisList", 2, 1);

        System.out.println(jedisList);
    }
}
