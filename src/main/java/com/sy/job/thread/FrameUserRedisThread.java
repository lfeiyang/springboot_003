package com.sy.job.thread;

import com.sy.model.FrameUser;
import com.sy.service.impl.FrameUserService;
import com.sy.util.RedisCatchUtil;
import com.sy.util.SpringUtil;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 用户缓存线程
 *
 * @author lfeiyang
 * @since 2022-05-03 1:39
 */
public class FrameUserRedisThread implements Runnable {
    private final RedisCatchUtil redisCatchUtil = SpringUtil.getBean("redisCatchUtil");

    private final FrameUserService frameUserService = SpringUtil.getBean("frameUserService");

    private int first = 0;

    private int pageSize = 0;

    public FrameUserRedisThread() {

    }

    public FrameUserRedisThread(int first, int pageSize) {
        this.first = first;
        this.pageSize = pageSize;
    }

    @Override
    public void run() {
        try {
            // 获取当前线程需要处理的用户数据
            List<FrameUser> frameUserList = frameUserService.getLocalFrameUserList(first, pageSize);
            if (CollectionUtils.isEmpty(frameUserList)) {
                return;
            }

            for (FrameUser frameUser : frameUserList) {
                FrameUser user = (FrameUser) redisCatchUtil.get(frameUser.getUserGuid());
                if (user != null) {
                    continue;
                }

                redisCatchUtil.set(frameUser.getUserGuid(), frameUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
