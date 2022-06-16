package com.wisdge.cloud.activity.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@ApiModel("公告对象")
@TableName("MSG_NOTICE")
public class Notice {

    @TableId(value="ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty("ID")
    private String id;

    @TableField("CATALOG_ID")
    @ApiModelProperty("类型ID")
    private String catalogId;

    @TableField(value = "CATALOG_NAME", exist = false)
    @ApiModelProperty("类型")
    private String catalogName;

    @TableField("SUBJECT")
    @ApiModelProperty("主题")
    private String subject;

    @TableField("PRIORITY")
    @ApiModelProperty("紧急优先")
    private boolean priority;

    @TableField("RECEIVER")
    @ApiModelProperty("接收角色")
    private String receiver;

    @TableField("DELAY_TIME")
    @ApiModelProperty("延迟时间")
    private Date delayTime;

    @TableField("DEPRECATED_TIME")
    @ApiModelProperty("延迟时间")
    private Date deprecatedTime;

    @TableField(value = "CREATE_BY", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("创建人ID")
    private String createBy;

    @TableField(value = "CREATE_BY_NAME", exist = false)
    @ApiModelProperty("创建人姓名")
    private String createByName;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("创建时间")
    private Date createTime;

    @TableField(value = "READ_COUNT", exist = false)
    @ApiModelProperty("阅读次数")
    private int readCount;

    @TableField(value = "REPLY_COUNT", exist = false)
    @ApiModelProperty("回复次数")
    private int replyCount;

    @TableField(value = "READ_FLAG", exist = false)
    @ApiModelProperty("阅读状态")
    private boolean readFlag;

    @TableField(value = "REPLYABLE")
    @ApiModelProperty("可回复")
    private boolean replyable;

    @TableField(value = "STATUS")
    @ApiModelProperty("状态")
    private int status;

    @TableField(value = "CONTENT")
    @ApiModelProperty("内容")
    private String content;

    @TableField(value = "RESULT")
    @ApiModelProperty("返回值")
    private String result;
}
