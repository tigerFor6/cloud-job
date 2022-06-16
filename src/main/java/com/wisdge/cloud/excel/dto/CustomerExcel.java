package com.wisdge.cloud.excel.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.wisdge.cloud.excel.converter.AreaConverter;
import com.wisdge.cloud.excel.converter.CityConverter;
import com.wisdge.cloud.excel.converter.GenderConverter;
import com.wisdge.cloud.excel.converter.ProvinceConverter;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author Carlos.Chen
 * @Date 2021/5/28
 */
@Data
public class CustomerExcel implements Serializable {

    /** 客户ID */
    @ExcelProperty(value = "客户ID")
    private String id;
    @ExcelIgnore
    private String taskId;
    @ExcelIgnore
    private String exceptType;

    /** 用户姓名 */
    @ExcelProperty(value = "*用户姓名")
    private String fullname;

    /** 性别 */
    @ExcelProperty(value = "性别", converter = GenderConverter.class)
    private String gender;

    /** 联系电话 */
    @ExcelProperty(value = "*联系电话")
    private String phone;


    /** 年龄 */
    @ExcelProperty(value = "年龄")
    private String age;

    /** 身份证号 */
    @ExcelProperty(value = "身份证号")
    private String idCard;

    /** 行政省 */
    @ExcelProperty(value = "省份", converter = ProvinceConverter.class)
    private String provinceId;

    /** 行政市 */
    @ExcelProperty(value = "市", converter = CityConverter.class)
    private String cityId;

    /** 行政区 */
    @ExcelProperty(value = "区", converter = AreaConverter.class)
    private String areaId;

    /** 行政街道 */
    @ExcelProperty(value = "街道")
    private String countyId;

    /** 行政社区 */
    @ExcelProperty(value = "社区")
    private String communityId;

//    /** 联系地址 */
//    @ExportEntityMap(CnName="联系地址",EnName="address")
//    private String address;

    /** 详细地址 */
    @ExcelProperty(value = "用详细地址户姓名")
    private String fullAddress;

    /** 电子邮箱 */
    @ExcelProperty(value = "电子邮箱")
    private String email;

    /** QQ */
    @ExcelProperty(value = "QQ")
    private String qq;

    /** 微信号 */
    @ExcelProperty(value = "微信号")
    private String wechatId;

    /** 钉钉号 */
    @ExcelProperty(value = "钉钉号")
    private String dingdingId;

    /** 组织ID */
    @ExcelProperty(value = "组织ID")
//    @ExportEntityMap(CnName="组织ID",EnName="orgId")
    private String orgId;

    /** 部门ID */
    @ExcelProperty(value = "部门ID")
//    @ExportEntityMap(CnName="部门ID",EnName="deptId")
    private String deptId;

    /** 头像地址 */
    @ExcelProperty(value = "头像地址")
    private String avatar;

    /** 职级 */
    @ExcelProperty(value = "职级")
    private String level;

    private Date lastLoginTime;

    private String lastLoginIp;

    /** 状态（1正常，0停用，-1过期,  -2被锁,  -3密码过期, -9删除） */
    private String status;

    /** 注册渠道 */
    @ExcelProperty(value = "注册渠道")
    private String createForm;

    /** 创建人 */
    private String createBy;

    /** 创建时间 */
    private Date createTime;

    /** 修改人 */
    private String updateBy;

    /** 修改时间 */
    private Date updateTime;

    /** 渠道聊天群 */
    @ExcelProperty(value = "渠道聊天群")
    private String wechatForm;

    /** 备注 */
    @ExcelProperty(value = "备注")
    private String remark;
}
