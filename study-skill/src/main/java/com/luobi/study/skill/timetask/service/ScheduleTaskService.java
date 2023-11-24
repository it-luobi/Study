package com.luobi.study.skill.timetask.service;

import com.luobi.study.skill.timetask.config.ScheduleTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ScheduleTaskService {

    private final ScheduleTask scheduleTask;

    @Autowired
    public ScheduleTaskService(ScheduleTask scheduleTask) {
        this.scheduleTask = scheduleTask;
    }

    public void updateTimeTaskCron(String cron) {
        log.info("new cron :{}", cron);
        scheduleTask.setCron(cron);
    }

    /**
     * 通过注解方式添加定时任务 cron表达式(0 0/5 * * * ?)表示每5分钟执行一次定时任务
     * 这个定时任务是额外开的，它的执行时间间隔不会被影响
     */
    @Scheduled(cron = "${printTime.cron}")
    public void executePrintTimeTask() {
        log.info(">>>>>>>>>>>>>>>>>>定时任务开始执行");
        System.out.println("正在执行定时任务~");
        log.info(">>>>>>>>>>>>>>>>>>定时任务执行结束");
    }

}
