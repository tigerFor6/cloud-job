package com.wisdge.cloud.activity.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wisdge.cloud.activity.entity.Notice;
import com.wisdge.cloud.activity.entity.NoticeUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NoticeUserDao extends BaseMapper<NoticeUser> {

    @Select("select n.* from MSG_NOTICE n, MSG_NOTICE_USER nu where n.id=nu.NOTICE_ID and n.SUBJECT=#{subject} and nu.USER_ID=#{userId}")
    List<Notice> findCountNotice(@Param("subject") String subject, @Param("userId") String userId);

}
