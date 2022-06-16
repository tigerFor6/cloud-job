package com.wisdge.cloud.excel.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * 客户信息表
 *
 * @author tiger
 * @date 2021-07-28
 */
@Data
@ApiModel("客户信息")
@TableName("customer")
public class Customer {

    /**
     * 客户ID
     */
    @TableId(value="ID")
    private String id;

    /**
     * 华夏ods库中的客户id
     */
    @TableField(value="CUSTID")
    private String CUSTID;

    /**
     * 用户姓名
     */
    @TableField(value = "CUSTOMER_NAME")
    private String customerName;

    /**
     * 性别
     */
    @TableField(value = "GENDER")
    private String gender;


    /**
     * 联系电话
     */
    @TableField(value = "PHONE")
    private String phone;
    /**
     * 固话
     */
    @TableField(value = "WORKPHONE")
    private String workPhone;

    /**
     * 年龄
     */
    @TableField(value = "AGE")
    private String age;

    /**
     * 客户类型
     */
    @TableField(value = "CUSTOMER_TYPE")
    private String customerType;

    /**
     * 证件类型
     */
    @TableField(value = "ID_TYPE")
    private String idType;

    /**
     * 证件号码
     */
    @TableField(value = "ID_CARD")
    private String idCard;

    /**
     * 行政省码
     */
    @TableField(value = "PROVINCE_ID")
    private String provinceId;


    /**
     * 行政市码
     */
    @TableField(value = "CITY_ID")
    private String cityId;

    /**
     * 行政区码
     */
    @TableField(value = "AREA_ID")
    private String areaId;


    /**
     * 行政社区码
     */
    @TableField(value = "COMMUNITY_ID")
    private String communityId;


    /**
     * 行政社区码
     */
    @TableField(value = "COUNTY_ID")
    private String countyId;

    /**
     * 邮编
     */
    @TableField(value = "CUST_POST")
    private String custPost;


    /**
     * 联系地址
     */
    @TableField(value = "ADDRESS")
    private String address;

    /**
     * 详细地址
     */
    @TableField(value = "FULL_ADDRESS")
    private String fullAddress;

    /**
     * 电子邮箱
     */
    @TableField(value = "EMAIL")
    private String email;

    /**
     * QQ
     */
    @TableField(value = "QQ")
    private String qq;

    /**
     * 生日
     */
    @TableField(value = "BIRTHDAY")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date birthday;

    /**
     * 微信号
     */
    @TableField(value = "WECHAT_ID")
    private String wechatId;

    /**
     * 微信号uid
     */
    @TableField(value = "UNION_ID")
    private String unionId;

    /**
     * 微信号uid状态
     */
    @TableField(value = "UNION_STATUS")
    private String unionStatus;

    /**
     * 钉钉号
     */
    @TableField(value = "DINGDING_ID")
    private String dingdingId;

    /**
     * 基金账号
     */
    @TableField(value = "FUND_ACCOUNT")
    private String fundAccount;

    /**
     * 交易账号
     */
    @TableField(value = "TRADING_ACCOUNT")
    private String tradingAccount;
    /**
     * 风险等级
     */
    @TableField(value = "RISK_LEVEL")
    private String riskLevel;
    /**
     * 服务网点
     */
    @TableField(value = "NETWORK")
    private String network;

    /**
     * 组织ID
     */
    @TableField(value = "ORG_ID")
    private String orgId;

    /**
     * 客户经理名字
     */
    @TableField(value = "MANAGE")
    private String managerName;

    /**
     * 客户经理名字
     */
    @TableField(value = "SERVICE_ADMIN_ID")
    private String serviceAdminId;


    /**
     * 部门ID
     */
    @TableField(value = "DEPT_ID")
    private String deptId;


    /**
     * 备注
     */
    @TableField(value = "REMARK")
    private String remark;

    /**
     * 渠道聊天群
     */
    @TableField(value = "WECHAT_FORM")
    private String wechatForm;


    /**
     * 创建人
     */
    @TableField(value = "CREATE_BY")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * 修改人
     */
    @TableField(value = "UPDATE_BY")
    private String updateBy;

    /**
     * 修改时间
     */
    @TableField(value = "UPDATE_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;
}
