package com.wisdge.cloud.activity.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wisdge.cloud.activity.entity.AsyncJobEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 异步任务
 *
 * @author tiger
 * @date 2021-06-03 19:50:28
 */
@Mapper
public interface AsyncJobDao extends BaseMapper<AsyncJobEntity> {

}
