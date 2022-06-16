package com.wisdge.cloud.activity.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wisdge.cloud.activity.entity.CustomerEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 客户
 *
 * @author tiger
 * @date 2021-06-03 19:50:28
 */
@Mapper
public interface CustomerDao extends BaseMapper<CustomerEntity> {

}
