package com.wisdge.cloud.activity.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 活动触发条件信息表
 *
 * @author tiger
 * @date 2021-05-13 19:39:49
 */
@Data
@TableName("activity_condition")
@ApiModel("活动触发条件信息表")
public class ActivityConditionEntity implements Serializable {
    /**
     * 主键id
     */
    @ApiModelProperty("主键ID")
    private Long id;

    /**
     * 条件名称
     */
    @ApiModelProperty("条件名称")
    private String name;

    /**
     * 条件规则
     */
    @ApiModelProperty("条件规则")
    private String rule;
}
