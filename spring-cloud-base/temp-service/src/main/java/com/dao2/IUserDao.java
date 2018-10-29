package com.dao2;

import com.bean.User;

public interface IUserDao
{
    User findUserByUsernameAndPassword(String userName, String password) throws Exception;
    
}
