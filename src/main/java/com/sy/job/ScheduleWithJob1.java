package com.sy.job;

import com.sy.service.impl.ScheduleJobService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Job1
 *
 * @author lfeiyang
 * @since 2022-05-04 23:49
 */
@Component
public class ScheduleWithJob1 extends QuartzJobBean {
    @Autowired
    private ScheduleJobService scheduleJobService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("start schedule job1: " + LocalDateTime.now());
        try {
            scheduleJobService.scheduleJob1();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
