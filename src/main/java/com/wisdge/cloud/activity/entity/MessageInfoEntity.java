package com.wisdge.cloud.activity.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 短信邮件发送信息表
 *
 * @author tiger
 * @date 2021-06-29 09:40:11
 */
@Data
@TableName("t_message_info")
@ApiModel("短信邮件发送信息表")
public class MessageInfoEntity {
    /**
     * 主键id
     */
    @TableId
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("业务流水")
    private Long businessId;

    @ApiModelProperty("类型 短信：1，邮件：2")
    private int type;

    @ApiModelProperty("电话/邮箱")
    private String source;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("状态")
    private int status;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private String createBy;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

    @ApiModelProperty("描述")
    private String description;
}
