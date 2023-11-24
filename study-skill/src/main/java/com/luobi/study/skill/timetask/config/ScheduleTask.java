package com.luobi.study.skill.timetask.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 用于修改定时任务时间间隔的配置
 */
@Data
@Slf4j
@Component
@PropertySource("classpath:/timetask/task-config.ini")
public class ScheduleTask implements SchedulingConfigurer {

    @Value("${defaultTime.cron}")
    private String cron;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        // 动态使用cron表达式设置循环间隔
        taskRegistrar.addTriggerTask(
                // 1. 添加任务内容（Runnable）
                () -> log.info("Current time : {}", LocalDateTime.now()),
                // 2. 设置执行周期（Trigger）
                triggerContext -> {
                    // 使用CronTrigger触发器，可动态修改cron表达式来操作循环规则
                    CronTrigger cronTrigger = new CronTrigger(cron);
                    // 返回执行周期
                    return cronTrigger.nextExecutionTime(triggerContext);
                }
        );
    }

}
