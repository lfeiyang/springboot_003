package com.sy.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * xml配置定时任务（无需集成任何接口或实现类）
 *
 * @author lfeiyang
 * @since 2022-05-06 1:45
 */
@Slf4j
@Component
public class QuartzDataXmlJob {
    public void execute() {
        log.info("quartzDataXmlJob");
    }
}
