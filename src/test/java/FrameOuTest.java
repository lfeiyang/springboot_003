import com.github.pagehelper.PageHelper;
import com.sy.config.SpringConfig;
import com.sy.model.FrameOu;
import com.sy.service.IFrameOuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 部门单元测试
 *
 * @author lfeiyang
 * @since 2022-05-01 21:02
 */
@SpringBootTest(classes = SpringConfig.class)
@RunWith(value = SpringJUnit4ClassRunner.class)
public class FrameOuTest {
    @Autowired
    private IFrameOuService ouService;

    @Test
    public void getFrameOuList(){
        PageHelper.startPage(1, 1);

        List<FrameOu> ouList = ouService.selectAll();

        System.out.println(ouList);
    }
}
