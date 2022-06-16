package com.wisdge.cloud.excel.service.impl;

import com.wisdge.cloud.excel.dao.ExcelCustomerDao;
import com.wisdge.cloud.excel.dto.CustomerExcel;
import com.wisdge.cloud.excel.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Carlos.Chen
 * @Date 2021/5/12
 */
@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    ExcelCustomerDao excelCustomerDao;

    @Override
    public int batchCreate(List<CustomerExcel> map) {
        return excelCustomerDao.batchCreate(map);
    }

    @Override
    public int batchCreateTemp(List<CustomerExcel> map) {
        return excelCustomerDao.batchCreateTemp(map);
    }

    @Override
    public List<String> verifyPhone(List<String> phone) {
        return excelCustomerDao.verifyPhone(phone);
    }

    @Override
    public int update(CustomerExcel map) {
        return excelCustomerDao.update(map);
    }
}
