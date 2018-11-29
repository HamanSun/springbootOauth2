package com.iss.base.common;

import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Customize the MyBatis Mapper plug-in interface, if you need other interfaces
 * please refer to the official document to add.
 */
public interface SystemMapper<T> extends Mapper<T>, MySqlMapper<T>, IdsMapper<T>, ConditionMapper<T> {
}
