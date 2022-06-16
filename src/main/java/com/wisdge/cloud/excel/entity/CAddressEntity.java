package com.wisdge.cloud.excel.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 地址
 *
 */
@Data
@ApiModel("地址")
public class CAddressEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 社区ID
     */
    @ApiModelProperty("社区ID")
    private String communityId;
    /**
     * 社区
     */
    @ApiModelProperty(value = "社区")
    private String communityDesc;

    /**
     * 街道ID
     */
    @ApiModelProperty("街道ID")
    private String countyId;

    /**
     * 社区
     */
    @ApiModelProperty(value = "街道")
    private String countyDesc;
    /**
     * 行政区ID
     */
    @ApiModelProperty("行政区ID")
    private String areaId;
    /**
     * 行政区
     */
    @ApiModelProperty(value = "行政区")
    private String areaDesc;

    /**
     * 城市ID
     */
    @ApiModelProperty("城市ID")
    private String cityId;
    /**
     * 城市
     */
    @ApiModelProperty(value = "城市")
    private String cityDesc;

    /**
     * 省份ID
     */
    @ApiModelProperty("省份ID")
    private String provinceId;
    /**
     * 省份
     */
//    @JsonIgnore
    @ApiModelProperty(value = "省份")
    private String provinceDesc;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;
    /**
     * 拼音首字母
     */
    @ApiModelProperty(value = "拼音首字母")
    private String pinyinPrefix;

}
