package com.sy.service;

/**
 * ScheduleJobService接口类
 *
 * @author lfeiyang
 * @since 2022-05-04 23:52
 */
public interface IScheduleJobService {
    /**
     * job1定时任务
     */
    void scheduleJob1() throws Exception;

    /**
     * job2定时任务
     */
    void scheduleJob2() throws Exception;

    /**
     * job1定时任务
     */
    void job1() throws Exception;
}
