package com.wisdge.cloud.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@ToString
@ApiModel("用户表")
@TableName("SYS_USER")
public class User {
    @TableId(value="id", type = IdType.ASSIGN_ID)
    @ApiModelProperty("ID")
    private String id;

    @ApiModelProperty("登录名")
    private String name;

    @ApiModelProperty("姓名")
    private String fullname;

    @ApiModelProperty("性别")
    private int gender;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("移动电话")
    private String phone;

    @ApiModelProperty("工号")
    private String empId;

    @ApiModelProperty("状态")
    private int status;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("所在组织")
    private String orgId;

    @ApiModelProperty("所在组织名称")
    @TableField(exist = false)
    private String orgName;

    @ApiModelProperty("备注")
    private String comment;

    @ApiModelProperty("创建者")
    private String createBy;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新者")
    private String updateBy;

    @ApiModelProperty("更新时间")
    private Date updateTime;
}
