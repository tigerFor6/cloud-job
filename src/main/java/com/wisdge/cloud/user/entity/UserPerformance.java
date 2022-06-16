package com.wisdge.cloud.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@ApiModel("用户绩效信息表")
@TableName("user_performance")
public class UserPerformance {
    @TableId(value="ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty("ID")
    private String id;

    @TableField("user_id")
    @ApiModelProperty("用户id")
    private String userId;

    @TableField(value = "customer_num")
    @ApiModelProperty("管理的客户数")
    private int customerNum;

    @TableField(value = "event_num")
    @ApiModelProperty("创建事件数")
    private int eventNum;

    @TableField(value = "task_num")
    @ApiModelProperty("待办任务数")
    private int taskNum;

    @TableField(value = "create_time")
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    @TableField(value = "update_time")
    @ApiModelProperty("修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;


}
