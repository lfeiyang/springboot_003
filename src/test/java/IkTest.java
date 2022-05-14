import com.sy.config.SpringConfig;
import com.sy.util.IKAnalyzerUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;

/**
 * Ik分词器测试
 *
 * @author lfeiyang
 * @since 2022-05-14 22:00
 */
@SpringBootTest(classes = {SpringConfig.class})
@RunWith(value = SpringJUnit4ClassRunner.class)
public class IkTest {
    @Test
    public void ik() throws IOException {
        String text = "哈哈哈哈中国太平成立九十周年了哈哈哈哈！";
        List<String> list = IKAnalyzerUtil.cut(text);
        System.out.println(list);
    }
}
