package com.my.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.bean.User;
import com.my.dao.IUserDao;
import com.my.service.IUserService;

@Service
public class UserServiceImpl implements IUserService
{
    @Autowired
    private IUserDao userDao;
    
    @Override
    public User findByUsernameAndPassword(String userName, String password) throws Exception
    {
        return userDao.findUserByUsernameAndPassword(userName, password);
    }
    
}
