package com.wisdge.cloud.activity.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wisdge.cloud.activity.entity.ActivityEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 活动
 *
 * @author tiger
 * @date 2021-05-13 16:36:20
 */
@Mapper
public interface ActivityDao extends BaseMapper<ActivityEntity> {

    /**
     * 逾期任务
     * <p>
     * 逾期前一天和逾期当天
     * </P>
     *
     * @return
     */
    List<ActivityEntity> selectLateActivity();
}
