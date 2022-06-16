package com.wisdge.cloud.quartz.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 任务入参
 *
 * @author tiger
 * @date 2021-06-03 14:57:21
 */
@Data
@ApiModel("任务入参")
public class JobDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    // 服务名称
    private String triggerName;

    // 归属系统名称
    private String triggerGroupName;

    // 执行周期
    private String cron;

    // 入参
    private String jobData;

    // 执行的逻辑module类名称
    private String moduleName;

}
