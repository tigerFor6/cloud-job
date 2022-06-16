package com.wisdge.cloud.excel.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wisdge.cloud.excel.entity.CAddressEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author Carlos.Chen
 * @Date 2021/5/12
 */
@Mapper
public interface AddressDao {

    List<CAddressEntity> findProvince(@Param("map")Map map);

    List<CAddressEntity> findCity(@Param("map")Map map);

    List<CAddressEntity> findArea(@Param("map")Map map);

    List<CAddressEntity> findCounty(@Param("map")Map map);

    List<CAddressEntity> findCommunity(@Param("map")Map map);

    List<CAddressEntity> findAll();
}
