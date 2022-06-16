package com.wisdge.cloud.excel.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 客户信息
 *
 */
@Data
@ApiModel("客户信息")
public class CustomerEntity implements Serializable {
    /**
     * 客户ID
     */
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
     * 性别
     */
    private String genderDesc;

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
     * 行政省码
     */
    private String provinceDesc;

    /**
     * 行政市码
     */
    private String cityId;

    private String cityDesc;

    /**
     * 行政区码
     */
    private String areaId;

    private String areaDesc;

    /**
     * 行政社区码
     */
    private String communityId;

    private String communityDesc;

    /**
     * 行政社区码
     */
    private String countyId;

    private String countyDesc;

    /**
     * 联系地址
     */
    private String address;

    /**
     * 详细地址
     */
    private String fullAddress;

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
     * 头像地址
     */
    private String avatar;

    /**
     * 职级
     */
    private String level;

    private Date lastLoginTime;

    private String lastLoginIp;

    /**
     * 状态（1正常，0停用，-1过期,  -2被锁,  -3密码过期, -9删除）
     */
    private String status;

    //最近的分配状态
    private String recentStatus;

    private String recentStatusDesc;

    private Date recentDistributionTime;

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

    /**
     * 备注
     */
    private String remark;

    /**
     * 渠道聊天群
     */
    private String wechatForm;

    private List<Map> userList;

    private List<Map> tagList;

    private static final Long serialVersionUID = 1L;
}
