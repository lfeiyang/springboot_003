package com.sy.job;

import com.sy.service.impl.ScheduleJobService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * [一句话注释]
 *
 * @author lfeiyang
 * @since 2022-05-05 0:31
 */
@Slf4j
@Component
public class Job1 extends QuartzJobBean {
    @Autowired
    private ScheduleJobService scheduleJobService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("start schedule job1: " + LocalDateTime.now());

        try {
            scheduleJobService.job1();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
