package com.wisdge.cloud.excel.service.impl;

import com.wisdge.cloud.excel.entity.CAddressEntity;
import com.wisdge.cloud.excel.service.AddressCacheService;
import com.wisdge.cloud.excel.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @Author Carlos.Chen
 * @Date 2021/7/13
 */
@Service("AddressCacheService")
public class AddressCacheServiceImpl implements AddressCacheService {

    @Autowired
    private AddressService addressService;

    private HashMap<String, String> provinceIdMap = new HashMap<>();
    private HashMap<String, String> provinceDescMap = new HashMap<>();
    private HashMap<String, String> cityIdMap = new HashMap<>();
    private HashMap<String, String> cityDescMap = new HashMap<>();
    private HashMap<String, String> areaIdMap = new HashMap<>();
    private HashMap<String, String> areaDescMap = new HashMap<>();

    private void getCache() {
        List<CAddressEntity> regionList = addressService.findAll();;
        for(CAddressEntity entity : regionList) {
            provinceIdMap.put(entity.getProvinceId(), entity.getProvinceDesc());
            provinceDescMap.put(entity.getProvinceDesc(), entity.getProvinceId());
            cityIdMap.put(entity.getCityId(), entity.getCityDesc());
            cityDescMap.put(entity.getCityDesc(), entity.getCityId());
            areaIdMap.put(entity.getAreaId(), entity.getAreaDesc());
            areaDescMap.put(entity.getAreaDesc(), entity.getAreaId());
        }
    }

    @Override
    public String getProvinceIdByDesc(String key) {
        if(provinceDescMap.size() == 0) {
            getCache();
        }
        String value = provinceDescMap.get(key);
        return value;
    }

    @Override
    public String getProvinceDescById(String key) {
        if(provinceIdMap.size() == 0) {
            getCache();
        }
        return provinceIdMap.get(key);
    }

    @Override
    public String getCityIdByDesc(String key) {
        if(cityDescMap.size() == 0) {
            getCache();
        }
        return cityDescMap.get(key);
    }

    @Override
    public String getCityDescById(String key) {
        if(cityIdMap.size() == 0) {
            getCache();
        }
        return cityIdMap.get(key);
    }

    @Override
    public String getAreaIdByDesc(String key) {
        if(areaDescMap.size() == 0) {
            getCache();
        }
        return areaDescMap.get(key);
    }

    @Override
    public String getAreaDescById(String key) {
        if(areaIdMap.size() == 0) {
            getCache();
        }
        return areaIdMap.get(key);
    }

}
