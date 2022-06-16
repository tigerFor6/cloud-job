package com.wisdge.cloud.quartz.controller;

import com.alibaba.fastjson.JSON;
import com.wisdge.cloud.dto.ApiResult;
import com.wisdge.cloud.quartz.dto.JobDTO;
import com.wisdge.cloud.util.QuartzJobUtils;
import io.swagger.annotations.ApiOperation;
import org.quartz.JobDataMap;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Description
 *
 * @author tiger
 * @since: 2021/6/3
 */
@RestController
@RequestMapping(value = "/api")
public class QuartzJobController {

    @Autowired
    private Scheduler scheduler;

    @PostMapping("/createJob")
    @ApiOperation(value = "创建任务", notes = "创建任务")
    public ApiResult createJob(@RequestBody JobDTO jobDTO) throws ClassNotFoundException {
        String path = "com.wisdge.cloud.quartz.job."+ jobDTO.getModuleName();
        Class jobClass = Class.forName(path);
        Map jobDataMap = JSON.parseObject(jobDTO.getJobData(), Map.class);
        QuartzJobUtils.createJob(scheduler, jobDTO.getTriggerName(), jobDTO.getTriggerGroupName(), jobClass,
                jobDTO.getCron(),new JobDataMap(jobDataMap));
        return ApiResult.ok("创建成功", null);
    }

    @PostMapping("/modifyJobTime")
    @ApiOperation(value = "修改定时任务执行时间", notes = "修改定时任务执行时间")
    public ApiResult modifyJobTime(@RequestBody JobDTO jobDTO) {
        QuartzJobUtils.modifyJobTime(scheduler, jobDTO.getTriggerName(), jobDTO.getTriggerGroupName(), jobDTO.getCron());
        return ApiResult.ok("修改成功", null);
    }

    @PostMapping("/pauseJob")
    @ApiOperation(value = "暂停一个job", notes = "暂停一个job")
    public ApiResult pauseJob(@RequestBody JobDTO jobDTO) {
        QuartzJobUtils.pauseJob(scheduler, jobDTO.getTriggerName(), jobDTO.getTriggerGroupName());
        return ApiResult.ok( "修改成功", null);
    }

    @PostMapping("/resumeJob")
    @ApiOperation(value = "恢复一个任务", notes = "恢复一个任务")
    public ApiResult resumeJob(@RequestBody JobDTO jobDTO) {
        QuartzJobUtils.resumeJob(scheduler, jobDTO.getTriggerName(), jobDTO.getTriggerGroupName());
        return ApiResult.ok("修改成功", null);
    }

    @PostMapping("/closeJob")
    @ApiOperation(value = "移除一个任务", notes = "移除一个任务")
    public ApiResult closeJob(@RequestBody JobDTO jobDTO) {
        QuartzJobUtils.close(scheduler, jobDTO.getTriggerName(), jobDTO.getTriggerGroupName(), jobDTO.getTriggerName(), jobDTO.getTriggerGroupName());
        return ApiResult.ok("修改成功", null);
    }

    @PostMapping("/getJobStatus")
    @ApiOperation(value = "查询任务状态", notes = "查询任务状态")
    public ApiResult getJobStatus(@RequestBody JobDTO jobDTO) {
        /**
         *
         * NONE,不存在
         * NORMAL,正常
         * PAUSED,暂停
         * COMPLETE,完成
         * ERROR,错误
         * BLOCKED;阻塞
         */
        Trigger.TriggerState jobStatus = QuartzJobUtils.getJobStatus(scheduler, jobDTO.getTriggerName(), jobDTO.getTriggerGroupName());
        return ApiResult.ok("查询成功", jobStatus);
    }
}
