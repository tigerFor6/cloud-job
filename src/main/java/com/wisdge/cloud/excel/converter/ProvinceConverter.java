package com.wisdge.cloud.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wisdge.cloud.excel.service.AddressCacheService;
import com.wisdge.cloud.util.SpringApplicationUtils;
import org.apache.commons.lang3.StringUtils;

import javax.print.DocFlavor;
import java.util.Map;

/**
 * @Author Carlos.Chen
 * @Date 2021/5/28
 */
public class ProvinceConverter implements Converter<String> {

    private AddressCacheService cacheService;

    public ProvinceConverter() {
        cacheService = SpringApplicationUtils.getBean(AddressCacheService.class);
    }

    private Page<Map> queryPage = new Page<>(1, 1);

    @Override
    public Class supportJavaTypeKey() {
        return DocFlavor.STRING.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public String convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return cacheService.getProvinceIdByDesc(cellData.getStringValue());
    }

    @Override
    public CellData convertToExcelData(String value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String desc = "";
        if(!StringUtils.isEmpty(value)) {
            desc =  cacheService.getProvinceDescById(value);
        }
        return new CellData(desc);
    }
}
