package com.sy.config;

import com.sy.job.thread.FrameUserRedisThread;
import com.sy.model.FrameUser;
import com.sy.service.IFrameUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 配置类
 *
 * @author lfeiyang
 * @since 2022-04-23 21:53
 */
// 扫描包（不然要和其它包在同层级，否则扫描不到）
@SpringBootApplication(scanBasePackages = "com.sy")
@MapperScan(value = {"com.sy.mapper"})
public class SpringConfig implements CommandLineRunner {
    /**
     * 默认10个线程
     */
    public static final int THREADNUMS = 10;

    /**
     * 实际线程数
     **/
    public static int loopNum = 0;

    @Autowired
    private IFrameUserService frameUserService;


    /***
     * 项目启动后立即执行的操作
     *
     * @param args 参数
     **/
    @Override
    public void run(String... args) throws Exception {
        // initRedisData();
    }

    private void initRedisData() {
        ExecutorService executorService = Executors.newFixedThreadPool(THREADNUMS);
        try {
            int pageSize = getPageSize(THREADNUMS);
            for (int i = 0; i < loopNum; i++) {
                executorService.execute(new FrameUserRedisThread(i * pageSize, pageSize));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 每个线程数据量
     *
     * @param threadNums 线程数
     * @return int
     **/
    public int getPageSize(int threadNums) {
        FrameUser frameUser = new FrameUser();
        int selectAllCount = frameUserService.selectAllCount(frameUser);

        // 循环次数
        loopNum = threadNums;

        // 单个线程处理数据量
        int pageSize = selectAllCount / threadNums + 1;

        if (selectAllCount <= threadNums) {
            loopNum = 1;

            return selectAllCount;
        }

        return pageSize;
    }


}
