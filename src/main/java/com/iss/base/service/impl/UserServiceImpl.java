package com.iss.base.service.impl;

import com.iss.base.mapper.UserMapper;
import com.iss.base.model.User;
import com.iss.base.service.UserService;
import com.iss.base.common.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by Junjie Sun on 2018/11/19.
 */
@Service
@Transactional
public class UserServiceImpl extends AbstractService<User> implements UserService {
    @Resource
    private UserMapper userMapper;

}
