package com.wisdge.cloud.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import javax.print.DocFlavor;

/**
 * @Author Carlos.Chen
 * @Date 2021/5/28
 */
public class GenderConverter implements Converter<String> {
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
        return "男".equals(cellData.getStringValue()) ? "0" : "女".equals(cellData.getStringValue()) ? "1" : "-1";
    }

    @Override
    public CellData convertToExcelData(String value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new CellData(value.equals("1") ? "女" : value.equals("0") ? "男" : "未知");
    }
}
