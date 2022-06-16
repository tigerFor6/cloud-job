package com.wisdge.cloud.activity.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 异步任务信息
 *
 * @author tiger
 * @date 2021-06-3 19:40:11
 */
@Data
@TableName("t_async_job")
@ApiModel("异步任务信息")
public class AsyncJobEntity {
    /**
     * 主键id
     */
    @TableId
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("任务名称")
    private String name;

    @ApiModelProperty("任务类型")
    private int type;

    @ApiModelProperty("备注")
    private String comment;

    @ApiModelProperty("业务流水")
    private Long businessId;

    @ApiModelProperty("状态")
    private int status;

    @ApiModelProperty("返回值")
    private String result;

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
     * 修改人
     */
    @ApiModelProperty("修改人")
    private String updateBy;
    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;
}
