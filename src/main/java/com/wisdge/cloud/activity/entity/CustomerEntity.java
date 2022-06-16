package com.wisdge.cloud.activity.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户信息
 *
 */
@Data
@TableName("customer")
@ApiModel("客户信息")
public class CustomerEntity implements Serializable {
    /**
     * 主键id
     */
    @TableId
    @ApiModelProperty("主键ID")
    private String id;

    /**
     * 用户姓名
     */
    private String fullname;

    /**
     * 性别
     */
    private String gender;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 年龄
     */
    private String age;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 行政省码
     */
    private String provinceId;

    /**
     * 行政市码
     */
    private String cityId;

    /**
     * 行政区码
     */
    private String areaId;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * QQ
     */
    private String qq;

    /**
     * 微信号
     */
    private String wechatId;

    /**
     * 钉钉号
     */
    private String dingdingId;

    /**
     * 组织ID
     */
    private String orgId;

    /**
     * 部门ID
     */
    private String deptId;

    /**
     * 状态（1正常，0停用，-1过期,  -2被锁,  -3密码过期, -9删除）
     */
    private String status;

    /**
     * 注册渠道
     */
    private String createForm;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 修改时间
     */
    private Date updateTime;

    private static final Long serialVersionUID = 1L;
}
