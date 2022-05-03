import com.sy.config.SpringConfig;
import com.sy.util.RedisCatchUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * redis计数器场景测试
 *
 * @author lfeiyang
 * @since 2022-05-03 22:55
 */
@SpringBootTest(classes = {SpringConfig.class})
@RunWith(value = SpringJUnit4ClassRunner.class)
public class JedisCounterTest {
    @Autowired
    private RedisCatchUtil redisCatchUtil;

    /**
     * 限流
     **/
    @Test
    public void limitFlowTest() {
        for (int i = 0; i < 10000; i++) {
            if (limitFlow("Jedis", 60L)) {
                System.out.println("已限流");

                break;
            }
        }
    }

    public boolean limitFlow(String key, Long expireMillis) {
        long incr = redisCatchUtil.incr(key, 1);
        if (incr == 1) {
            redisCatchUtil.expire(key, expireMillis);
        }

        System.out.println("60s内第" + incr + "次访问");

        if (incr > 1000) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    /**
     * 幂等
     **/
    @Test
    public void barrierTest() {
        if (barrier("order", 60L)) {
            System.out.println("幂等");
        }
    }

    public boolean barrier(String key, Long expireMillis) {
        long incr = redisCatchUtil.incr(key, 1);
        if (incr == 1) {
            redisCatchUtil.expire(key, expireMillis);
        }

        if (incr > 1) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }
}
