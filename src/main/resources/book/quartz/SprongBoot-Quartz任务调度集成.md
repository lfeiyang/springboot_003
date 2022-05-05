# <font face=幼圆 color=white>[SpringBoot集成Quartz任务调度](http://www.quartz-scheduler.org/)</font>

## <font face=幼圆 color=white>一、什么是Quartz</font>

### <font face=幼圆 color=white>1.1.Job任务</font>

​		<font face=幼圆 color=white>JobDetail主要由JobKey（job的名字name和分组group）、JobClass、JobDataMap（任务相关的数据）、JobBuilder组成。常用的是前几个。</font>

#### <font face=幼圆 color=white>1.1.1.源码</font>

```java
package org.quartz;

import java.io.Serializable;

public interface JobDetail extends Serializable, Cloneable {
    JobKey getKey();

    String getDescription();

    Class<? extends Job> getJobClass();

    JobDataMap getJobDataMap();

    boolean isDurable();

    boolean isPersistJobDataAfterExecution();

    boolean isConcurrentExectionDisallowed();

    boolean requestsRecovery();

    Object clone();

    JobBuilder getJobBuilder();
}
```

#### <font face=幼圆 color=white>1.1.2.示例</font>

````java
Map<String,String> jobData = new HashMap<>();
String jobName = "schedulerJob";
String jobGroup = "schedulerGroup";
jobData.put("key00", "value00");
JobDetail jobDetail = JobBuilder.newJob(SchedulerJob.class)
                	.withIdentity(jobName, jobGroup)
                	.usingJobData("key01", "value01")
                	.usingJobData(jobData)
                	.storeDurably()
                	.build();
````



### <font face=幼圆 color=white>1.2.Trigger触发器</font>

​		<font face=幼圆 color=white>Trigger规定触发执行Job实现类，主要有SimpleTrigger和CronTrigger两个实现类。Trigger由以下部分组成：</font>

1. <font face=幼圆 color=white>TriggerKey（job的名字name和分组group）</font>

2. <font face=幼圆 color=white>JobDataMap（Trigger相关的数据，同JobDetail中JobDataMap，存相同key，若value不同，会覆盖前者。）</font>

3. <font face=幼圆 color=white>ScheduleBuilder（有CronScheduleBuilder、SimpleScheduleBuilder、CalendarIntervalScheduleBuilder、DailyTimeIntervalScheduleBuilder常用前2种。）</font>

#### <font face=幼圆 color=white>1.2.1.示例</font>

```java
//SimpleScheduleBuilder
String triggerName = "schedulerJob";
String triggerGroup = "schedulerGroup";
Trigger trigger = TriggerBuilder
    			.newTrigger()
    			.withIdentity(triggerName, triggerGroup)
    			.withSchedule(SimpleScheduleBuilder)
    			.repeatSecondlyForever(1)
    			.withIntervalInSeconds(0)
    			.withRepeatCount(0))
    			.startNow()
    			.build();

//CronScheduleBuilder
String triggerName2 = "schedulerJob2";
String triggerGroup2 = "schedulerGroup2";
String jobTime = "0 0 * * * ?";
Trigger trigger2 = TriggerBuilder
				.newTrigger()
				.withIdentity(triggerName2, triggerGroup2)
				.startAt(DateBuilder.futureDate(1, IntervalUnit.SECOND))
				.withSchedule(CronScheduleBuilder.cronSchedule(jobTime))
				.startNow()
				.build();

```



### <font face=幼圆 color=white>1.3.Scheduler调度器</font>

 	<font face=幼圆 color=white>调度器就是为了读取触发器Trigger从而触发定时任务JobDetail。可以通过SchedulerFactory进行创建调度器，分为`StdSchedulerFactory（常用）和DirectSchedulerFactory`两种。</font>

1. <font face=幼圆 color=white>StdSchedulerFactory使用一组属性（放在配置文件中）创建和初始化调度器，然后通过`getScheduler()`方法生成调度程序。</font>
2. <font face=幼圆 color=white>DirectSchedulerFactory不常用，容易硬编码。</font>

#### <font face=幼圆 color=white>1.3.1.示例</font>

```java
//建好jobDetail,trigger
... ...
//StdSchedulerFactory方式，用的多
SchedulerFactory schedulerFactory = new StdSchedulerFactory();
Scheduler schedulerStd = schedulerFactory.getScheduler();

//DirectSchedulerFactory方式
DirectSchedulerFactory directSchedulerFactory = DirectSchedulerFactory.getInstance();
Scheduler schedulerDir=directSchedulerFactory.getScheduler();

//执行调度
schedulerStd.scheduleJob(jobDetail, trigger);
schedulerStd.start();
```

### <font face=幼圆 color=white>1.4.依赖引用</font>

```java
<!-- 6.任务调度 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-quartz</artifactId>
</dependency>
    
<!-- 2.增强文档 -->
<dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>knife4j-spring-boot-starter</artifactId>
    <version>3.0.3</version>
</dependency>
```



## <font face=幼圆 color=white>二、简单通过配置设置定时任务</font>

### <font face=幼圆 color=white>2.1.任务类</font>

#### <font face=幼圆 color=white>2.1.1.具体业务</font>

```java
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
```



#### <font face=幼圆 color=white>2.1.2.具体任务类</font>

```java
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
```



```java
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
```



### <font face=幼圆 color=white>2.2.配置类</font>

```java
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
```



### <font face=幼圆 color=white>1.3.自动执行器注解</font>

```java
package com.sy.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * 启动类
 *
 * @author lfeiyang
 * @since 2022-04-23 21:54
 */
@EnableAutoConfiguration
@EnableWebMvc
public class SpringBootStarter {
    public static void main(String[] args) {
        SpringApplication.run(SpringConfig.class, args);
    }
}
```

## <font face=幼圆 color=white>三、动态定时任务和持久化</font>

### <font face=幼圆 color=white>3.1.实体</font>

#### <font face=幼圆 color=white>3.1.1.JobXXXBean</font>

```java
package com.sy.bean;

import lombok.Data;

/**
 * [一句话注释]
 *
 * @author lfeiyang
 * @since 2022-05-05 0:20
 */
@Data
public class JobXXXBean {
    private String key01;
    private String key02;
    private String key03;
    private String key04;
    private String jobTimeCron;
    private String openBean;
}
```



#### <font face=幼圆 color=white>3.2.1.UpdateBean</font>

```java
package com.sy.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 更新job cron时间参数
 *
 * @author lfeiyang
 * @since 2022-05-05 0:39
 */
@ApiModel(value = "更新job cron时间参数")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateJobBean {
    @ApiModelProperty(value = "jobTime的cron表达式", example = "0 0 1 * * ?")
    String jobCronTime;

    public String getJobCronTime() {
        return jobCronTime;
    }

    public void setJobCronTime(String jobCronTime) {
        this.jobCronTime = jobCronTime;
    }
}
```



### <font face=幼圆 color=white>3.2.增删改查业务层</font>

#### <font face=幼圆 color=white>3.2.1.增删改查业务层接口</font>

```java
package com.sy.service;

import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;
import java.util.Map;

/**
 * 通过程序动态对定时任务进行增删改查接口类
 *
 * @author lfeiyang
 * @since 2022-05-04 21:55
 */
public interface IQuartzService {
    /**
     * 增加一个任务job
     *
     * @param jobClass     任务job实现类
     * @param jobName      任务job名称（保证唯一性）
     * @param jobGroupName 任务job组名
     * @param jobTime      任务时间间隔（秒）
     * @param jobTimes     任务运行次数（若<0，则不限次数）
     * @param jobData      任务参数
     */
    void addJob(Class<? extends QuartzJobBean> jobClass, String jobName, String jobGroupName, int jobTime, int jobTimes, Map<? extends String, ?> jobData);

    /**
     * 增加一个任务job
     *
     * @param jobClass     任务job实现类
     * @param jobName      任务job名称（保证唯一性）
     * @param jobGroupName 任务job组名
     * @param jobTime      任务时间表达式
     * @param jobData      任务参数
     */
    void addJob(Class<? extends QuartzJobBean> jobClass, String jobName, String jobGroupName, String jobTime, Map<? extends String, ?> jobData);

    /**
     * 修改一个任务job
     *
     * @param jobName      任务名称
     * @param jobGroupName 任务组名
     * @param jobTime      cron时间表达式
     */
    void updateJob(String jobName, String jobGroupName, String jobTime);

    /**
     * 删除一个任务job
     *
     * @param jobName      任务名称
     * @param jobGroupName 任务组名
     */
    void deleteJob(String jobName, String jobGroupName);

    /**
     * 暂停一个任务job
     *
     * @param jobName      任务名称
     * @param jobGroupName 任务组名
     */
    void pauseJob(String jobName, String jobGroupName);

    /**
     * 恢复一个任务job
     *
     * @param jobName      任务名称
     * @param jobGroupName 任务组名
     */
    void resumeJob(String jobName, String jobGroupName);

    /**
     * 立即执行一个任务job
     *
     * @param jobName      任务名称
     * @param jobGroupName 任务组名
     */
    void runAJobNow(String jobName, String jobGroupName);

    /**
     * 获取所有任务job
     *
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     **/
    List<Map<String, Object>> queryAllJob();

    /**
     * 获取正在运行的任务job
     *
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     */
    List<Map<String, Object>> queryRunJob();
}
```



#### <font face=幼圆 color=white>3.2.2.增删改查业务层接口实现</font>

```java
package com.sy.service.impl;

import com.sy.exception.BaseException;
import com.sy.service.IQuartzService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 通过程序动态对定时任务进行增删改查接口实现类
 *
 * @author lfeiyang
 * @since 2022-05-04 22:08
 */
@Slf4j
@Service
public class QuartzService implements IQuartzService {
    @Autowired
    private Scheduler scheduler;

    @PostConstruct
    public void startScheduler() {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 增加一个任务job
     *
     * @param jobClass     任务job实现类
     * @param jobName      任务job名称（保证唯一性）
     * @param jobGroupName 任务job组名
     * @param jobTime      任务时间间隔（秒）
     * @param jobTimes     任务运行次数（若<0，则不限次数）
     * @param jobData      任务参数
     */
    @Override
    public void addJob(Class<? extends QuartzJobBean> jobClass, String jobName, String jobGroupName, int jobTime, int jobTimes, Map<? extends String, ?> jobData) {
        try {
            // 任务名称和组构成任务key
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).storeDurably().build();

            // 设置job参数
            if (jobData != null && jobData.size() > 0) {
                jobDetail.getJobDataMap().putAll(jobData);
            }

            // 触发器使用simpleTrigger规则
            Trigger trigger = null;
            if (jobTimes < 0) {
                trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroupName)
                        .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1).withIntervalInSeconds(jobTime))
                        .startNow().build();
            } else {
                trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroupName)
                        .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1).withIntervalInSeconds(jobTime).withRepeatCount(jobTimes))
                        .startNow().build();
            }

            // 调度器
            scheduler.scheduleJob(jobDetail, trigger);

            log.info("jobDataMap: {}", jobDetail.getJobDataMap().getWrappedMap());
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new BaseException("add job error!");
        }
    }

    /**
     * 增加一个任务job
     *
     * @param jobClass     任务job实现类
     * @param jobName      任务job名称（保证唯一性）
     * @param jobGroupName 任务job组名
     * @param jobTime      任务时间表达式
     * @param jobData      任务参数
     */
    @Override
    public void addJob(Class<? extends QuartzJobBean> jobClass, String jobName, String jobGroupName, String jobTime, Map<? extends String, ?> jobData) {
        try {
            // 创建jobDetail实例，绑定Job实现类
            // 指明job的名称，所在组的名称，以及绑定job类
            // 任务名称和组构成任务key
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();

            // 设置job参数
            if (jobData != null && jobData.size() > 0) {
                jobDetail.getJobDataMap().putAll(jobData);
            }

            // 定义调度触发规则
            // 使用cornTrigger规则
            // 触发器key
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroupName)
                    .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND))
                    .withSchedule(CronScheduleBuilder.cronSchedule(jobTime)).startNow().build();

            // 把作业和触发器注册到任务调度中
            scheduler.scheduleJob(jobDetail, trigger);

            log.info("jobDataMap: {}", jobDetail.getJobDataMap());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("add job error!");
        }
    }

    /**
     * 修改一个任务job
     *
     * @param jobName      任务名称
     * @param jobGroupName 任务组名
     * @param jobTime      cron时间表达式
     */
    @Override
    public void updateJob(String jobName, String jobGroupName, String jobTime) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            log.info("new jobTime: {}", jobTime);
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(CronScheduleBuilder.cronSchedule(jobTime)).build();

            // 重启触发器
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new BaseException("update job error!");
        }
    }

    /**
     * 删除一个任务job
     *
     * @param jobName      任务名称
     * @param jobGroupName 任务组名
     */
    @Override
    public void deleteJob(String jobName, String jobGroupName) {
        try {
            scheduler.deleteJob(new JobKey(jobName, jobGroupName));
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("delete job error!");
        }
    }

    /**
     * 暂停一个任务job
     *
     * @param jobName      任务名称
     * @param jobGroupName 任务组名
     */
    @Override
    public void pauseJob(String jobName, String jobGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new BaseException("pause job error!");
        }
    }

    /**
     * 恢复一个任务job
     *
     * @param jobName      任务名称
     * @param jobGroupName 任务组名
     */
    @Override
    public void resumeJob(String jobName, String jobGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new BaseException("resume job error!");
        }
    }

    /**
     * 立即执行一个任务job
     *
     * @param jobName      任务名称
     * @param jobGroupName 任务组名
     */
    @Override
    public void runAJobNow(String jobName, String jobGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new BaseException("run a job error!");
        }
    }

    /**
     * 获取所有任务job
     *
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     **/
    @Override
    public List<Map<String, Object>> queryAllJob() {
        List<Map<String, Object>> jobList = null;

        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);

            jobList = new ArrayList<Map<String, Object>>();
            for (JobKey jobKey : jobKeys) {
                log.info("maps: {}", scheduler.getJobDetail(jobKey).getJobDataMap().getWrappedMap());

                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("jobName", jobKey.getName());
                    map.put("jobGroupName", jobKey.getGroup());
                    map.put("description", "触发器:" + trigger.getKey());

                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    map.put("jobStatus", triggerState.name());

                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        String cronExpression = cronTrigger.getCronExpression();
                        map.put("jobTime", cronExpression);
                    }

                    jobList.add(map);
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new BaseException("query all jobs error!");
        }

        return jobList;
    }

    /**
     * 获取正在运行的任务job
     *
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     */
    @Override
    public List<Map<String, Object>> queryRunJob() {
        List<Map<String, Object>> jobList = null;
        try {
            List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
            jobList = new ArrayList<Map<String, Object>>(executingJobs.size());
            for (JobExecutionContext executingJob : executingJobs) {
                Map<String, Object> map = new HashMap<String, Object>();
                JobDetail jobDetail = executingJob.getJobDetail();

                JobKey jobKey = jobDetail.getKey();
                Trigger trigger = executingJob.getTrigger();
                map.put("jobName", jobKey.getName());
                map.put("jobGroupName", jobKey.getGroup());
                map.put("description", "触发器:" + trigger.getKey());

                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                map.put("jobStatus", triggerState.name());

                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    map.put("jobTime", cronExpression);
                }

                jobList.add(map);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new BaseException("query run jobs error!");
        }

        return jobList;
    }
}
```

### <font face=幼圆 color=white>3.3.增删改查控制层</font>

```java
package com.sy.controller;

import com.sy.bean.JobXXXBean;
import com.sy.bean.UpdateJobBean;
import com.sy.exception.BadRequestException;
import com.sy.job.Job1;
import com.sy.service.impl.QuartzService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.quartz.JobDataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Quartz控制层
 *
 * @author lfeiyang
 * @since 2022-05-05 0:03
 */
@RestController
public class QuartzController {
    @Autowired
    private QuartzService quartzService;

    @ApiOperation(value = "使用quartz添加job")
    @RequestMapping(value = "/addJob/{jobUUID}", method = RequestMethod.POST)
    public void addQuartzJob(@ApiParam(name = "jobUUID") @PathVariable("jobUUID") String jobUUID, @ApiParam(name = "JobXXXBean") @RequestBody JobXXXBean jobXXXBean) {
        if (jobXXXBean.getOpenBean() != null) {
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("key01", jobXXXBean.getKey01());
            jobDataMap.put("key02", jobXXXBean.getKey02());
            jobDataMap.put("key03", jobXXXBean.getKey03());
            jobDataMap.put("jobTimeCron", jobXXXBean.getJobTimeCron());
            jobDataMap.put("key04", jobXXXBean.getKey04());

            quartzService.addJob(Job1.class, jobUUID, jobUUID, jobXXXBean.getJobTimeCron(), jobDataMap);
        } else {
            throw new BadRequestException("参数错误");
        }
    }

    @ApiOperation(value = "使用quartz查询所有job")
    @RequestMapping(value = "/queryAllJob", method = RequestMethod.GET)
    public List<Map<String, Object>> queryAllQuartzJob() {
        return quartzService.queryAllJob();
    }

    @ApiOperation(value = "使用quartz查询所有运行job")
    @RequestMapping(value = "/queryRunJob", method = RequestMethod.GET)
    public List<Map<String, Object>> queryRunQuartzJob() {
        return quartzService.queryRunJob();
    }

    @ApiOperation(value = "使用quartz删除job")
    @RequestMapping(value = "/deleteJob/{jobUUID}", method = RequestMethod.DELETE)
    public void deleteJob(@ApiParam(name = "jobUUID") @PathVariable("jobUUID") String jobUUID) {
        quartzService.deleteJob(jobUUID, jobUUID);
    }

    @ApiOperation(value = "使用quartz修改job的cron时间")
    @RequestMapping(value = "/updateJob/{jobUUID}", method = RequestMethod.PUT)
    public void deleteJob(@ApiParam(name = "jobUUID") @PathVariable("jobUUID") String jobUUID, @ApiParam(name = "jobCronTime") @RequestBody UpdateJobBean updateJobBean) {
        quartzService.updateJob(jobUUID, jobUUID, updateJobBean.getJobCronTime());
    }
}
```



### <font face=幼圆 color=white>3.4.Quartz数据表脚本</font>

### <font face=幼圆 color=white>3.5.持久化</font>

<font face=幼圆 color=white>application.properties</font>

```properties
#Quartz
#持久化配置
spring.quartz.job-store-type=jdbc

#Spring Boot 2.6.X使用PathPatternMatcher匹配路径，Swagger引用的Springfox使用的路径匹配是基于AntPathMatcher的，添加配置
spring.mvc.pathmatch.matching-strategy=ANT_PATH_MATCHER

# Cron表达式
schedule.cron.withJob1=0 0/2 * * * ?
schedule.cron.withJob2=0 0/2 * * * ?
```

