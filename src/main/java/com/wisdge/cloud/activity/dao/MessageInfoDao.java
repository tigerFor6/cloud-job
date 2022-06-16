package com.wisdge.cloud.activity.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wisdge.cloud.activity.entity.MessageInfoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 短信邮件信息
 *
 * @author tiger
 * @date 2021-06-29 11:50:28
 */
@Mapper
public interface MessageInfoDao extends BaseMapper<MessageInfoEntity> {

}
