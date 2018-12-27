package com.my.dao;

import com.my.bean.User;

public interface IUserDao
{
    User findUserByUsernameAndPassword(String userName, String password) throws Exception;
    
}
