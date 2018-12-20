package com.app.user.mapper;

import com.app.common.mapper.BaseMapper;
import com.app.user.entity.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper extends BaseMapper<User> {

}
