package com.wisdge.cloud.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wisdge.cloud.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserDao extends BaseMapper<User> {

    @Select("select U.* " +
            "from SYS_USER U " +
            "where U.STATUS = 1 and U.ID NOT IN " +
            "(select user_id from USER_PERFORMANCE)")
    List<User> findPerformanceUser();
}
