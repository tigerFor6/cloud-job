package com.wisdge.cloud.excel.service;

/**
 * @Author Carlos.Chen
 * @Date 2021/5/12
 */
public interface AddressCacheService {

    public String getProvinceIdByDesc(String key);

    public String getProvinceDescById(String key);

    public String getCityIdByDesc(String key);

    public String getCityDescById(String key);

    public String getAreaIdByDesc(String key);

    public String getAreaDescById(String key);

}
