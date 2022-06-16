package com.wisdge.cloud.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wisdge.cloud.user.entity.UserPerformance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserPerformanceDao extends BaseMapper<UserPerformance> {

    void updateByUserId();
}
