package com.service;

import com.bean.User;

public interface IUserService
{
    
    User findByUsernameAndPassword(String username, String password) throws Exception;
    
}
