package com.sy.job;

import com.sy.service.impl.QuartzService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * 任务
 *
 * @author lfeiyang
 * @since 2022-05-04 23:39
 */
@Slf4j
@Component
public class SchedulerJob extends QuartzJobBean {
    @Autowired
    private QuartzService quartzService;

    @Value("${schedule.cron.withJob1}")
    private String cronTimeJob1;

    public String getCronTimeJob1() {
        return cronTimeJob1;
    }

    @Value("${schedule.cron.withJob2}")
    private String cronTimeJob2;

    public String getCronTimeJob2() {
        return cronTimeJob2;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            // job1先删后增
            log.info("job1: delete scheduleWithJob1");
            quartzService.deleteJob("scheduleWithJob1", "scheduleWithJob1_Group1");

            log.info("job1: add scheduleWithJob1");
            quartzService.addJob(ScheduleWithJob1.class, "scheduleWithJob1", "scheduleWithJob1_Group1", cronTimeJob1, null);

            // 按小时定时的job先删后增
            log.info("job2: delete scheduleWithJob2");
            quartzService.deleteJob("scheduleWithJob2", "scheduleWithJob2_Group2");

            log.info("job2: add scheduleWithJob2");
            quartzService.addJob(ScheduleWithJob2.class, "scheduleWithJob2", "scheduleWithJob2_Group2", cronTimeJob2, null);
        } catch (Exception e) {
            log.error("quartz service scheduler job failed!");
            e.printStackTrace();
        }
    }
}
