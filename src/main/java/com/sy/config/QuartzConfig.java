package com.sy.config;

import com.sy.job.SchedulerJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 简单的通过配置文件设定的定时任务
 *
 * @author lfeiyang
 * @since 2022-05-04 23:38
 */
@Configuration
public class QuartzConfig {
    @Bean
    public JobDetail scheduleJobDetail() {
        System.out.println("**************************************** scheduler job begin");

        JobDetail jobDetail = JobBuilder.newJob(SchedulerJob.class)
                .withIdentity("schedulerJob")
                .storeDurably()
                .build();

        System.out.println("**************************************** scheduler job end");

        return jobDetail;
    }

    @Bean
    public Trigger scheduleJobDetailTrigger() {
        System.out.println("**************************************** schedulerJob trigger begin");

        Trigger trigger = TriggerBuilder
                .newTrigger()
                .forJob(scheduleJobDetail())
                .withIdentity("schedulerJob")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(0))
                .startNow()
                .build();

        System.out.println("**************************************** schedulerJob trigger end");

        return trigger;
    }
}
