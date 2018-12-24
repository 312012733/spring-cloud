package com.dao;

import com.bean.User;

public interface IUserDao
{
    User findUserByUsernameAndPassword(String userName, String password) throws Exception;
    
}
