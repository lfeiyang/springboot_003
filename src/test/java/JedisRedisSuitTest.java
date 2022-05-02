import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Redis打包测试
 *
 * @author lfeiyang
 * @since 2022-05-02 14:16
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(value = {JedisRedisTest.class})
public class JedisRedisSuitTest {
}
