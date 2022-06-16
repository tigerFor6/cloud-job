package com.wisdge.cloud.excel.service;

import com.wisdge.cloud.excel.dto.CustomerExcel;

import java.util.List;

/**
 * @Author Carlos.Chen
 * @Date 2021/5/12
 */
public interface CustomerService {

    List<String> verifyPhone(List<String> phone);

    int batchCreate(List<CustomerExcel> map);

    int batchCreateTemp(List<CustomerExcel> map);

    int update(CustomerExcel map);

}
