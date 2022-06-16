package com.wisdge.cloud.excel.dao;

import com.wisdge.cloud.excel.dto.CustomerExcel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Carlos.Chen
 * @Date 2021/5/12
 */
@Mapper
public interface ExcelCustomerDao {

    int batchCreate(@Param("list") List<CustomerExcel> map);

    int batchCreateTemp(@Param("list") List<CustomerExcel> map);

    List<String> verifyPhone(@Param("phones")List<String> phone);

    int update(@Param("map") CustomerExcel map);

}
