# <font face=幼圆 color=white>多线程</font>

## <font face=幼圆 color=white>一、线程中Bean的获取</font>

```java
package com.sy.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * spring工具类 方便在非spring管理环境中获取bean
 *
 * @author lfeiyang
 * @since 2022-05-03 2:01
 */
@Component
public class SpringUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        }
    }

    /**
     * 通过字节码获取
     *
     * @param beanClass 类字节码
     * @return T
     */
    public static <T> T getBean(Class<T> beanClass) {
        return applicationContext.getBean(beanClass);
    }

    /***
     * 通过BeanName获取
     *
     * @param beanName 类名
     * @return T
     **/
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    /**
     * 通过beanName和字节码获取
     *
     * @param name      类名
     * @param beanClass 类字节码
     * @return T
     */
    public static <T> T getBean(String name, Class<T> beanClass) {
        return applicationContext.getBean(name, beanClass);
    }

}
```

## <font face=幼圆 color=white>二、单个线程数据切割</font>

```java
/**
 * 默认10个线程
 */
public static final int THREADNUMS = 10;

/**
 * 实际线程数
 **/
public static int loopNum = 0;

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

/**
 * 单个线程数据切割
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
```

## <font face=幼圆 color=white>三、线程任务</font>

```java
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
```

