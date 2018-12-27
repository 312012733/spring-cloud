package com.my.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.my.bean.User;

@Mapper
public interface IUserDao extends BaseMapper<User>
{
    User findUserByUsernameAndPassword(String userName, String password) throws Exception;
    
}
