package com.wisdge.cloud.excel.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wisdge.cloud.excel.dao.AddressDao;
import com.wisdge.cloud.excel.entity.CAddressEntity;
import com.wisdge.cloud.excel.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author Carlos.Chen
 * @Date 2021/5/12
 */
@Service("AddressService")
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressDao addressDao;

    @Override
    public List<CAddressEntity> findProvince(Map map) {
        return addressDao.findProvince(map);
    }

    @Override
    public List<CAddressEntity> findCity(Map map) {
        return addressDao.findCity(map);
    }

    @Override
    public List<CAddressEntity> findArea(Map map) {
        return addressDao.findArea(map);
    }

    @Override
    public List<CAddressEntity> findCounty(Map map) {
        return addressDao.findCounty(map);
    }

    @Override
    public List<CAddressEntity> findCommunity(Map map) {
        return addressDao.findCommunity(map);
    }

    @Override
    public List<CAddressEntity> findAll() {
        return addressDao.findAll();
    }
}
