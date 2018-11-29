package com.iss.base.mapper;

import org.apache.ibatis.annotations.Param;

import com.iss.base.common.SystemMapper;
import com.iss.base.model.User;

public interface UserMapper extends SystemMapper<User> {
	User findByUserName(@Param("username") String username);
}