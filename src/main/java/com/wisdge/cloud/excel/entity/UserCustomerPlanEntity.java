package com.wisdge.cloud.excel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * user_customer
 * @author 
 */
@Data
@TableName("user_customer_plan")
@ApiModel("用户-客户关联信息")
public class UserCustomerPlanEntity implements Serializable {

    private static final Long serialVersionUID = 1L;
    /**
     * 关联ID
     */
    @TableId(value="id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 管理人ID
     */
    @TableField("USER_ID")
    private String userId;

    /**
     * 客户ID
     */
    @TableField("CUSTOMER_ID")
    private String customerId;

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
     * 权重
     */
    @TableField(value = "WEIGHT")
    private String weight;


    /**
     * 修改时间
     */
    @TableField(value = "ACTIVE_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date effectiveTime;

}