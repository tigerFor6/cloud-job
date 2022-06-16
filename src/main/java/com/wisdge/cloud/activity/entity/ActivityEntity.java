package com.wisdge.cloud.activity.entity;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 活动
 *
 * @author tiger
 * @date 2021-05-13 16:19:10
 */
@Data
@TableName("activity_info")
@ApiModel("活动信息")
public class ActivityEntity implements Serializable {

    /**
     * 主键id
     */
    @TableId
    @ApiModelProperty("主键ID")
    private String id;

    /**
     * 类型
     */
    @ApiModelProperty("类型")
    private Integer type;

    /**
     * 子类型
     */
    @ApiModelProperty("子类型")
    private Integer subtype;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endTime;

    /**
     * 任务目标
     */
    @ApiModelProperty("任务目标")
    private String target;

    /**
     * 名单类型
     * <p>
     * 0：上传Excel；1：神策标签;2：筛选客户;
     * <p/>
     */
    @ApiModelProperty("名单类型")
    private Integer listType;

    /**
     * 标签状态
     * <p>
     * 是否存储为神策标签
     * ：0非神策标签；1：神策标签
     * <p/>
     */
    @ApiModelProperty("标签状态")
    private Integer tagStatus;

    /**
     * 神策标签ID
     */
    @ApiModelProperty("神策标签ID")
    private Long tagSensorsId;

    /**
     * 任务详情
     */
    @ApiModelProperty("任务详情")
    private String description;

    /**
     * 任务状态
     */
    @ApiModelProperty("任务状态")
    private Integer status;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private String createBy;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 终止时间
     */
    @ApiModelProperty("终止时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date stopTime;

    /**
     * 处理人
     */
    @ApiModelProperty(value = "处理人", hidden = true)
    private String handler;

    /**
     * 处理人name
     */
    @ApiModelProperty(value = "处理人name", hidden = true)
    private String handlerNames;

    /**
     * 处理人集合
     */
    @ApiModelProperty(value = "处理人集合", hidden = true)
    @TableField(exist = false)
    private JSONArray handlersArray;

    /**
     * 相关素材
     */
    @ApiModelProperty(value = "相关素材", hidden = true)
    private String relatedMaterial;

    /**
     * 名单名称
     */
    @ApiModelProperty(value = "名单名称", hidden = true)
    private String listName;

}
