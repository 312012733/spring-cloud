package com.service;

import com.bean2.User;

public interface IUserService
{
    
    User findByUsernameAndPassword(String username, String password) throws Exception;
    
}
