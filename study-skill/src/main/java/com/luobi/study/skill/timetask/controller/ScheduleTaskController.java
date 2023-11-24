package com.luobi.study.skill.timetask.controller;

import com.luobi.study.skill.timetask.service.ScheduleTaskService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/time-task")
public class ScheduleTaskController {

    private final ScheduleTaskService scheduleTaskService;

    /**
     * 更改定时任务时间间隔的接口
     */
    @GetMapping("/updateCron")
    @ApiOperation(value = "更改定时任务时间间隔", notes = "每隔5秒钟执行一次 : 0/5 * * * * ?")
    public String updateTimeTaskCron(String cron) {
        scheduleTaskService.updateTimeTaskCron(cron);
        return "success";
    }

}
