package com.service;

import com.bean.User;

public interface IUserService
{
    
    User findUserByUsernameAndPassword(String userName, String password) throws Exception;
    
}
