package com.my.service;

import com.my.bean.User;

public interface IUserService
{
    
    User findByUsernameAndPassword(String username, String password) throws Exception;
    
}
