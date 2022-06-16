package com.wisdge.cloud.activity.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wisdge.cloud.activity.entity.ActivityTriggerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 活动
 *
 * @author tiger
 * @date 2021-05-13 16:36:20
 */
@Mapper
public interface ActivityTriggerDao extends BaseMapper<ActivityTriggerEntity> {

    @Select("SELECT activity_id,cron_express,trigger_type,touch_time, " +
            "condition_rule,official_account,template_id,content " +
            "FROM activity_trigger " +
            "WHERE activity_id = #{activityId}")
    ActivityTriggerEntity findActivityTriggerByActivityId(@Param("activityId") Long activityId);

}
