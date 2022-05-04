package com.sy.service.impl;

import com.sy.service.IScheduleJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * ScheduleJobService接口实现类
 *
 * @author lfeiyang
 * @since 2022-05-04 23:55
 */
@Slf4j
@Service
public class ScheduleJobService implements IScheduleJobService {
    @Override
    public void scheduleJob1() throws Exception {
        log.info("调度业务1");
    }

    @Override
    public void scheduleJob2() throws Exception {
        log.info("调度业务2");
    }

    @Override
    public void job1() throws Exception {
        log.info("job1");
    }
}
