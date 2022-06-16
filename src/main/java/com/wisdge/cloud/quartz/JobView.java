package com.wisdge.cloud.quartz;

import lombok.Data;

import java.util.Map;

/**
 * Description
 *
 * @author tiger
 * @since: 2021/5/13
 */
@Data
public class JobView {
    private String name;
    private String group;
    private String cronExpression;

    private String nextFireTime;
    private String status;
    Map<String, Object> params;

}
